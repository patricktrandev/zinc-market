package cloud.tientn.zinc.service;

import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.response.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> findAllCategory();
    Category findCategoryById(Long id);

}
