package cloud.tientn.zinc.controller;

import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.model.Role;
import cloud.tientn.zinc.response.CustomerDto;
import cloud.tientn.zinc.response.Response;
import cloud.tientn.zinc.response.TokenPair;
import cloud.tientn.zinc.response.converter.CustomerMapper;
import cloud.tientn.zinc.service.CustomerService;
import cloud.tientn.zinc.utils.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Response> createAccountByAnonymousUser(@RequestBody Customer customer){
        Customer savedAccount=customerService.createCustomer(customer);

        CustomerDto saved = CustomerMapper.convertToDto(savedAccount);
        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create account successfully",saved), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Response> loginByAnonymousUser(@RequestBody Customer customer){
        TokenPair token =customerService.authenticate(customer);
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"users info and jwt", token),HttpStatus.OK);
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<Response> getCustomerByIdAuthorizedByAdmin(@PathVariable Long id){
        CustomerDto found = CustomerMapper.convertToDto(customerService.viewProfileById(id));


        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find one successfully",found), HttpStatus.OK);
    }
    @GetMapping("/customers")
    public ResponseEntity<Response> getAllCustomersAuthorizedByAdmin(){
        List<CustomerDto> found = customerService.findAllCustomer().stream()
                .map(CustomerMapper::convertToDto).collect(Collectors.toList());
        Map<String, Object> data= new HashMap<>();
        data.put("numberOfCustomers",found.size());
        data.put("customers",found);


        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find all accounts successfully",data), HttpStatus.OK);
    }
    @PutMapping("/customers/{id}")
    public ResponseEntity<Response> addRoleToAccountsAuthorizedByAdmin(@PathVariable Long id, @RequestBody Role role){
        CustomerDto updated =CustomerMapper.convertToDto(customerService.updateRole(id,role.getName()));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Add role successfully",updated), HttpStatus.OK);
    }
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Response> removeRoleToAccountsAuthorizedByAdmin(@PathVariable Long id, @RequestBody Role role){
        CustomerDto removed =CustomerMapper.convertToDto(customerService.removeRoleToCustomerByAdmin(id, role.getName()));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Remove role successfully",removed), HttpStatus.OK);
    }
}
