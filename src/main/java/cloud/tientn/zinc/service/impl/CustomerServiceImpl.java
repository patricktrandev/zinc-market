package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.exception.ResourceAlreadyExistException;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.repository.CustomerRepository;
import cloud.tientn.zinc.response.TokenPair;
import cloud.tientn.zinc.service.CustomerService;
import cloud.tientn.zinc.utils.RoleUtils;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.jwt.secret}")
    private String signKey;


    @Override
    public Customer createCustomer(Customer customer) {
        Boolean found= customerRepository.existsByUsername(customer.getUsername());
        //Role foundRole= roleRepository.findByName(RoleUser.USER.name()).orElseThrow(()-> new ResourceNotFoundException(RoleUser.USER.name()));
        if(found){
            throw new ResourceAlreadyExistException(customer.getUsername());
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
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
            resultRole.append(r).append(" ");
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
            resultRole.append(r).append(" ");
        }
        found.setRole(String.valueOf(resultRole));
        return customerRepository.save(found);
    }

    @Override
    public TokenPair authenticate(Customer accountRequestAuth) {
        Customer found= customerRepository.findByUsername(accountRequestAuth.getUsername()).orElseThrow(()-> new ResourceNotFoundException("Username",accountRequestAuth.getUsername()));
        boolean check=passwordEncoder.matches(accountRequestAuth.getPassword(),found.getPassword());
        if(!check){
            throw new BadCredentialsException("401 Exception- User is not authenticated");
        }
        String token= generateToken(accountRequestAuth.getUsername(),found.getEmail(), found.getRole());
        TokenPair tokenPair= new TokenPair(token, null);
        return tokenPair;
    }

    private String generateToken(String username, String email,String role) {
        JWSHeader header= new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet= new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("cloud.tientn")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .claim("email",email)
                .claim("scope",role)
                .build();
        Payload payload= new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(signKey));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }

    }
}
