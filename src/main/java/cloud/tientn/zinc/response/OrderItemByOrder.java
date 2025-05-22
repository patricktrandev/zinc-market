package cloud.tientn.zinc.response;

import java.time.LocalDate;
import java.util.Date;

public interface OrderItemByOrder {
    public Long id();
    public Long couponId();
    public Double discount();
    public Date orderDate();
    public String shippingAddress();
    public Date processingDate();
    public String status();
    public Double total();
    public Long customerId();
    public Long orderId();
    public Long productId();
    public Integer quantity();
    public String name();
    public Float price();
    public String thumbnail();







}
