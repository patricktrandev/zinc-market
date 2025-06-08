package cloud.tientn.zinc.response.converter;

import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.response.CategoryDto;
import cloud.tientn.zinc.response.ProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CategoryMapper {

    public static Category convertToModel(CategoryDto source) {
        Category c= new Category();
        c.setId(source.getId());
        c.setName(source.getName());

        return c;
    }
    public static CategoryDto convertToDto(Category source) {
        List<ProductDto> productDtos= source.getProducts().stream().map(ProductMapper::convertToDto).toList();
        CategoryDto c= CategoryDto.builder()
                .id(source.getId())
                .name(source.getName())
                .numberOfProduct(source.getTotalProducts())
                .productDtos(productDtos)
                .build();
        return c;
    }
}
