package cloud.tientn.zinc.config.drools;

import cloud.tientn.zinc.model.Order;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.PriorityQueue;

public interface DroolsThread {
    KieSession getKieSession();
    void addFactToSession(List<Order> orderQueue);
    void interrupt();
    void start();
}
