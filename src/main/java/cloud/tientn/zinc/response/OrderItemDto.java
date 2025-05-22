package cloud.tientn.zinc.response;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = false,level = AccessLevel.PRIVATE)
public class OrderItemDto {
    Long productId;
    @Min(value = 1,message = "Quantity must be greater than 0")
    Integer quantity;
}
