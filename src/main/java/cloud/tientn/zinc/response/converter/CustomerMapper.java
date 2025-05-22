package cloud.tientn.zinc.response.converter;

import cloud.tientn.zinc.model.Customer;
import cloud.tientn.zinc.response.CustomerDto;


public class CustomerMapper {

    public static CustomerDto convertToDto(Customer customer){
        //List<OrderDto> list= customer.getOrders()!=null ?OrderMapper
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .role(customer.getRole())
                .address(customer.getAddress())
                .username(customer.getUsername())
                .membership(customer.getMembership())
                .totalOrders(customer.getNumberTotalOrder())
                .expenditure(customer.getExpenditure())
                //.orderDtoList()
                .build();

    }
    public static Customer convertToModel(CustomerDto customerDto){
        //List<OrderDto> orderDtoList= OrderMapper
        Customer customer= new Customer();
        customer.setId(customerDto.getId());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setRole(customerDto.getRole());
        customer.setUsername(customerDto.getUsername());
        customer.setMembership(customerDto.getMembership());
        customer.setExpenditure(customerDto.getExpenditure());
        //customer.setOrders();
        return customer;
    }
}
