package cloud.tientn.zinc.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Long id;
    private String name;
    private int numberOfProduct;

}
