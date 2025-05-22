package cloud.tientn.zinc.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@FieldDefaults(makeFinal = false,level = AccessLevel.PRIVATE)
public class CustomerDto {
    Long id;
    @NotEmpty(message = "username is required")
    String username;
    @NotEmpty(message = "email is required")
    @Email(message = "Email is not valid")
    String email;
    String role;
    String address;
    String membership;
    Integer totalOrders;
    Double expenditure;
    //List<OrderDto> orderDtoList;
}
