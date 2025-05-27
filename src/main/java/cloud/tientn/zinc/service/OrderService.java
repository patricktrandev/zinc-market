package cloud.tientn.zinc.service;

import cloud.tientn.zinc.response.OrderDto;
import cloud.tientn.zinc.response.OrderItemByOrderImpl;

import java.util.List;

public interface OrderService {
    Long createOrder(OrderDto orderDto,String username);
    List<OrderItemByOrderImpl> getOrderDetailsByOrderId(Long id );

    void processBatchOrders();
}
