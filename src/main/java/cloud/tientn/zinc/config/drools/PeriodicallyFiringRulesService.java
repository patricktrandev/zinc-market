package cloud.tientn.zinc.config.drools;

import cloud.tientn.zinc.model.Order;
import cloud.tientn.zinc.utils.TimeUtils;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class PeriodicallyFiringRulesService extends Thread implements DroolsThread{
    boolean run = false;
    final KieSession kieSession;
    public PeriodicallyFiringRulesService() {


        KieServices ks = KieServices.Factory.get();

        KieBaseConfiguration config = ks.newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        this.kieSession = ks.getKieClasspathContainer().newKieSession("ProcessOrderAutomated");
        this.run = true;
        KieBase kieBase = kieSession.getKieBase();
        List<Rule> rules = kieBase.getKiePackages().stream()
                .flatMap(pkg -> pkg.getRules().stream())
                .collect(Collectors.toList());
        System.out.println("Rules: " + rules);
        kieSession.addEventListener(new RuleRuntimeEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent objectInsertedEvent) {
                System.out.println("===> " + objectInsertedEvent.getObject() + " inserted");
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent objectUpdatedEvent) {
                System.out.println("===> " + objectUpdatedEvent + " updated");
            }

            @Override
            public void objectDeleted(ObjectDeletedEvent objectDeletedEvent) {
                System.out.println("===> " + objectDeletedEvent.getOldObject() + " deleted");
            }
        });

    }

    @Override
    public KieSession getKieSession() {
        return kieSession;
    }

    @Override
    public void addFactToSession(List<Order> orderQueue) {
        for (Order o : orderQueue) {
            System.out.print(TimeUtils.getCurrentTime());
            System.out.println("Inserting to session: " + o);
            kieSession.insert(o);
            sleepMs(1000);
        }



    }

    @Override
    public void interrupt() {
        run = false;
    }

    public void run() {
        while (run) {
            kieSession.fireAllRules();


            sleepMs(500);
        }
    }
        private void sleepMs(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Thread sleep interrupted");
        }
    }
}
