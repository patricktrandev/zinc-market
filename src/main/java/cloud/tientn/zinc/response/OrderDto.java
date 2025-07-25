package cloud.tientn.zinc.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@FieldDefaults(makeFinal = false,level = AccessLevel.PRIVATE)
public class OrderDto{
    Long id;
    String shippingAddress;
    LocalDateTime orderDate;
    LocalDateTime processingDate;
    Double total;
    Double discount;
    String status;
    String priority;
//    CustomerDto customerDto;
//    CouponDto couponDto;
    Set<OrderItemDto> orders;
}
