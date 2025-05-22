package cloud.tientn.zinc.repository;

import cloud.tientn.zinc.model.Order;
import cloud.tientn.zinc.model.OrderItem;

import cloud.tientn.zinc.response.OrderItemByOrderImpl;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Procedure(name = "processOrder")
    Long processOrder(Long p_customer_id, String p_shipping_address, Double p_discount, String p_status, String p_order_items);

    @Query(nativeQuery = true, value = "select o.id, o.coupon_id, o.discount, o.order_date, o.shipping_address, o.processing_date, o.status, o.total, customer_id, oi.order_id, oi.product_id, oi.quantity, p.name, p.price, p.thumbnail from orders as o \n" +
            "join orders_items as oi \n" +
            "on o.id= oi.order_id \n" +
            "join product as p on p.id= oi.product_id \n" +
            "where oi.order_id=:orderId")
    List<OrderItemByOrderImpl> queryOrderDetailsById(@Param("orderId") Long orderId);
}
