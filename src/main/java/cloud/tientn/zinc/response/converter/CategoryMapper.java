package cloud.tientn.zinc.response.converter;

import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.response.CategoryDto;
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

        CategoryDto c= CategoryDto.builder()
                .id(source.getId())
                .name(source.getName())
                .numberOfProduct(source.getTotalProducts())
                .build();
        return c;
    }
}
