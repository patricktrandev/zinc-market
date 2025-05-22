package cloud.tientn.zinc.service;

import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.response.CustomerDto;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer findByCustomerId(Long id);
    Customer findByCustomerByUsername(String name);
    List<Customer> findAllCustomer();
    Customer updateRole(Long id, String roleName);
    Customer removeRoleToCustomerByAdmin(Long id, String roleName);
}
