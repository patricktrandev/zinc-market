package cloud.tientn.zinc.response;

import cloud.tientn.zinc.model.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = false,level = AccessLevel.PRIVATE)
public class ProductDto{
        Long id;
        @NotEmpty(message = "Product name is required")
        String name;
        String description;
        Boolean isActive;
        Integer quantity;
        Float price;
        String thumbnail;
       // CategoryDto category;
}
