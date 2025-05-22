package cloud.tientn.zinc.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@FieldDefaults(makeFinal = false,level = AccessLevel.PRIVATE)
public class OrderDto{
    Long id;
    String shippingAddress;
    Date orderDate;
    Date processingDate;
    Double total;
    Double discount;
    String status;
//    CustomerDto customerDto;
//    CouponDto couponDto;
    Set<OrderItemDto> orders;
}
