package cloud.tientn.zinc.service.impl;

import cloud.tientn.zinc.exception.ResourceAlreadyExistException;
import cloud.tientn.zinc.exception.ResourceNotFoundException;
import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.repository.CategoryRepository;
import cloud.tientn.zinc.response.CategoryDto;
import cloud.tientn.zinc.response.converter.CategoryMapper;
import cloud.tientn.zinc.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public Category createCategory(CategoryDto categoryDto) {
        boolean check= categoryRepository.existsByName(categoryDto.getName());
        if(check){
            throw new ResourceAlreadyExistException(categoryDto.getName());
        }
        Category saved= categoryRepository.save(CategoryMapper.convertToModel(categoryDto));
        return saved;
    }

    @Override
    public List<Category> findAllCategory() {
        //List<Category> categoryDtos= categoryRepository.findAll();
        List<Category> categoryDtos= categoryRepository.findAllWithProducts();
        return categoryDtos;
    }

    @Override
    public Category findCategoryById(Long id) {
        Category found= categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id",id));

        return found;
    }
}
