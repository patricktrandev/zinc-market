package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.client.imageStorage.AzureStorageClientImpl;
import cloud.tientn.zinc.exception.ResourceAlreadyExistException;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.model.Product;
import cloud.tientn.zinc.repository.CategoryRepository;
import cloud.tientn.zinc.repository.ProductRepository;
import cloud.tientn.zinc.response.ProductDto;
import cloud.tientn.zinc.response.converter.ProductMapper;
import cloud.tientn.zinc.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Product createProduct(ProductDto productDto) {
        Product p= ProductMapper.convertToModel(productDto);
        Category category =categoryRepository.findByName(p.getCategory().getName()).orElseThrow(()-> new ResourceNotFoundException("Name",p.getCategory().getName()));
        boolean check= productRepository.existsByName(p.getName());
        if (check){
            throw new ResourceAlreadyExistException(p.getName());
        }

        p.setIsActive(true);
        p.addCategoryToProduct(category);
        Product product= productRepository.save(p);
        return product;
    }

    @Override
    public List<Product> findAllProducts(String name) {
        List<Product> products= new ArrayList<>();
        if(name!=null){
            Category category =categoryRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Name",name));
            products= productRepository.findAllByCategory_Id(category.getId());
        }else{
            products= productRepository.findAll();
        }

        return products;
    }

    @Override
    public Product findProductById(Long id) {
        Product p=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id", id));
        return p;
    }

    @Override
    public void deleteProduct(Long id) {
        Product p=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id", id));
        productRepository.deleteById(id);
    }

    @Override
    public Product updateCategory(Long id, ProductDto productDto) {
        Product product= ProductMapper.convertToModel(productDto);
        Product p=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id", id));
        if(product.getName()!=null){
            p.setName(product.getName());
        }
        if(product.getPrice()!=null){
            p.setPrice(product.getPrice());
        }
        if(product.getQuantity()!=null){
            p.setQuantity(product.getQuantity());
        }
        if(product.getCategory()!=null && product.getCategory().getName()!=null){
            p.removeCategoryToProduct(p.getCategory());
            Category category =categoryRepository.findByName(product.getCategory().getName()).orElseThrow(()-> new ResourceNotFoundException("Name",product.getCategory().getName()));
            p.addCategoryToProduct(category);
        }
        if(product.getThumbnail()!=null){
            p.setThumbnail(product.getThumbnail());
        }
        p.setIsActive(true);
        Product updated=productRepository.save(p);
        return updated;
    }
}
