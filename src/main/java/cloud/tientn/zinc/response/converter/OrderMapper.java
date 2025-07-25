package cloud.tientn.zinc.response.converter;

import cloud.tientn.zinc.model.Order;
import cloud.tientn.zinc.response.OrderDto;

public class OrderMapper {
    public static OrderDto convertToDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .discount(order.getDiscount())
                .total(order.getTotal()!=null? order.getTotal() : 0)
                .status(order.getStatus())
                .processingDate(order.getProcessingDate())
                .orderDate(order.getOrderDate())
                .shippingAddress(order.getShippingAddress())
                .priority(order.getPriority())
                //.couponDto(CouponMapper)
                
                //.clone(CustomerMapper.convertToDto(order.getCustomer()))
                .build();
    }
    public static Order convertToModel(OrderDto orderDto){
        Order order= new Order();
        order.setId(orderDto.getId());
        order.setOrderDate(orderDto.getOrderDate());
        order.setTotal(orderDto.getTotal());
        order.setDiscount(orderDto.getDiscount());
        order.setStatus(orderDto.getStatus());
        order.setProcessingDate(orderDto.getProcessingDate());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setOrderDate(orderDto.getOrderDate());
        order.setPriority(orderDto.getPriority());
        //order.setCoupon();
        //order.setCustomer(CustomerMapper.convertToModel(orderDto.getCustomerDto()));
        return order;
    }
}
