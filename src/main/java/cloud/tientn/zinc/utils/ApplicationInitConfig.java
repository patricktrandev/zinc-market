package cloud.tientn.zinc.utils;

import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    ApplicationRunner applicationRunner(CustomerRepository customerRepository){
        return args -> {
            if(customerRepository.findByUsername("admin").isEmpty()){
                Customer customer= new Customer();
                customer.setUsername("admin");
                customer.setPassword(passwordEncoder.encode("admin123"));
                customer.setRole("ADMIN");
                customer.setEmail("patricktrandev@gmail.com");
                customerRepository.save(customer);
                log.info("admin user has been created");
            }
        };
    }
}
