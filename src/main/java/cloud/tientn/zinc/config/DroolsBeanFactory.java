package cloud.tientn.zinc.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsBeanFactory {
    private KieServices kieServices = KieServices.Factory.get();

//    private KieFileSystem getKieFileSystem()  {
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/Discount_Order.drl"));
//        return kieFileSystem;
//    }
//
//    public KieSession getKieSession(){
//        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
//        kb.buildAll();
//        KieRepository kieRepository = kieServices.getRepository();
//        ReleaseId releaseId=kieRepository.getDefaultReleaseId();
//        KieContainer kContainer = kieServices.newKieContainer(releaseId);
//        return kContainer.newKieSession();
//    }

    public StatelessKieSession getKieSession(){
        // Tạo KieFileSystem
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        // Load file DRL từ classpath
        Resource drlResource = kieServices.getResources()
                .newClassPathResource("rules/Discount_Order.drl");

        // Thêm resource vào KieFileSystem
        kieFileSystem.write(drlResource);

        // Tạo KieBuilder
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        // Kiểm tra lỗi
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Error when building rule: " + results.getMessages());
        }

        // Tạo KieContainer
        KieContainer kieContainer = kieServices.newKieContainer(
                kieServices.getRepository().getDefaultReleaseId());

        // Tạo StatelessKieSession
        return kieContainer.newStatelessKieSession();
    }

}
