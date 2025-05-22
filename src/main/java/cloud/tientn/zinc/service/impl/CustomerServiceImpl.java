package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.exception.ResourceAlreadyExistException;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.repository.CustomerRepository;
import cloud.tientn.zinc.service.CustomerService;
import cloud.tientn.zinc.utils.RoleUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer createCustomer(Customer customer) {
        Boolean found= customerRepository.existsByUsername(customer.getUsername());
        //Role foundRole= roleRepository.findByName(RoleUser.USER.name()).orElseThrow(()-> new ResourceNotFoundException(RoleUser.USER.name()));
        if(found){
            throw new ResourceAlreadyExistException(customer.getUsername());
        }

        customer.setRole(RoleUtils.USER.name());
        return customerRepository.save(customer);
    }


    @Override
    public Customer findByCustomerId(Long id) {
        Customer found= customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id",id));
        return found;
    }

    @Override
    public Customer findByCustomerByUsername(String name) {
        Customer found= customerRepository.findByUsername(name).orElseThrow(()-> new ResourceNotFoundException("Name",name));
        return found;
    }

    @Override
    public List<Customer> findAllCustomer() {
        List<Customer> customers= customerRepository.findAll();
        return customers;
    }

    @Override
    public Customer updateRole(Long id, String roleName) {
        Customer found= customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id",id));
        String[] splitRole= roleName.split(" ");
        Set<String> roleSet= new HashSet<>();
        for(String r: splitRole){
            roleSet.add(r);
        }
        roleSet.add(roleName.toUpperCase());
        StringBuilder resultRole= new StringBuilder();
        for(String r: roleSet){
            resultRole.append(r).append("_");
        }
        found.setRole(String.valueOf(resultRole));
        return customerRepository.save(found);
    }

    @Override
    public Customer removeRoleToCustomerByAdmin(Long id, String roleName) {
        Customer found= customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id",id));
        String[] splitRole= roleName.split(" ");
        Set<String> roleSet= new HashSet<>();
        for(String r: splitRole){
            roleSet.add(r);
        }
        if(roleSet.contains(roleName)){
            roleSet.remove(roleName);
        }
        StringBuilder resultRole= new StringBuilder();
        for(String r: roleSet){
            resultRole.append(r).append("_");
        }
        found.setRole(String.valueOf(resultRole));
        return customerRepository.save(found);
    }
}
