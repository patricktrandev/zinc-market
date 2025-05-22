package cloud.tientn.zinc.service;

import cloud.tientn.zinc.model.Product;
import cloud.tientn.zinc.response.ProductDto;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDto productDto);
    List<Product> findAllProducts(String name);
    Product findProductById(Long id);
    void deleteProduct(Long id);
    Product updateCategory(Long id,ProductDto productDto);
}
