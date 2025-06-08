package cloud.tientn.zinc.service;

import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.response.TokenPair;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer viewProfileById(Long id);
    Customer findByCustomerByUsername(String name);
    List<Customer> findAllCustomer();
    Customer updateRole(Long id, String roleName);
    Customer removeRoleToCustomerByAdmin(Long id, String roleName);

    TokenPair authenticate(Customer accountRequestAuth);

}
