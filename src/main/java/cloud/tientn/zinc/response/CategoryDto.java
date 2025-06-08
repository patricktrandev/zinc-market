package cloud.tientn.zinc.response;

import cloud.tientn.zinc.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDto {

    private Long id;
    private String name;
    private int numberOfProduct;
    private List<ProductDto> productDtos;
}
