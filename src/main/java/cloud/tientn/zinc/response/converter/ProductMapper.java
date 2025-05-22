package cloud.tientn.zinc.response.converter;

import cloud.tientn.zinc.model.Product;
import cloud.tientn.zinc.response.ProductDto;

public class ProductMapper {

    public static Product convertToModel(ProductDto source){
        Product product=new Product();
        product.setId(source.getId());
        product.setName(source.getName());
        product.setCategory(source.getCategory()!=null?CategoryMapper.convertToModel(source.getCategory()):null);
        product.setIsActive(source.getIsActive());
        product.setDescription(source.getDescription());
        product.setQuantity(source.getQuantity());
        product.setPrice(source.getPrice());
        product.setThumbnail(source.getThumbnail());
        return product;
    }
    public static ProductDto convertToDto(Product source){
        return ProductDto.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .isActive(source.getIsActive())
                .category(source.getCategory()!=null? CategoryMapper.convertToDto(source.getCategory()):null)
                .quantity(source.getQuantity())
                .description(source.getDescription())
                .thumbnail(source.getThumbnail())
                .build();
    }
}
