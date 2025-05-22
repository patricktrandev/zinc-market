package cloud.tientn.zinc.repository;

import cloud.tientn.zinc.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Boolean existsByUsername(String username);
    Optional<Customer> findByUsername(String username);
}
