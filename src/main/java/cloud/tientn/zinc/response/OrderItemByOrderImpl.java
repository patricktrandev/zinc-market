package cloud.tientn.zinc.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderItemByOrderImpl implements OrderItemByOrder{
    private Long id;
    private Long couponId;
    private Double discount;
    private Date orderDate;
    private String shippingAddress;
    private Date processingDate;
    private String status;
    private Double total;
    private Long customerId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String name;
    private Float price;
    private String thumbnail;
    @Override
    public Long id() {
        return id;
    }

    @Override
    public Long couponId() {
        return couponId;
    }

    @Override
    public Double discount() {
        return discount;
    }

    @Override
    public Date orderDate() {
        return orderDate;
    }

    @Override
    public String shippingAddress() {
        return shippingAddress;
    }

    @Override
    public Date processingDate() {
        return processingDate;
    }

    @Override
    public String status() {
        return status;
    }

    @Override
    public Double total() {
        return total;
    }

    @Override
    public Long customerId() {
        return customerId;
    }

    @Override
    public Long orderId() {
        return orderId;
    }

    @Override
    public Long productId() {
        return productId;
    }

    @Override
    public Integer quantity() {
        return quantity;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Float price() {
        return price;
    }

    @Override
    public String thumbnail() {
        return thumbnail;
    }
}
