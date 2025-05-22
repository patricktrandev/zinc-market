package cloud.tientn.zinc.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = false,level = AccessLevel.PRIVATE)
public class CouponDto {

    Long id;
    @NotEmpty(message = "Code is required")
    String code;
    @NotNull(message = "Discount value is required")
    double discount;
    private Boolean isActive;
    String description;
}
