package cloud.tientn.zinc.controller;

import cloud.tientn.zinc.model.Category;
import cloud.tientn.zinc.response.CategoryDto;
import cloud.tientn.zinc.response.Response;
import cloud.tientn.zinc.response.converter.CategoryMapper;
import cloud.tientn.zinc.service.CategoryService;
import cloud.tientn.zinc.utils.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto){
       //CategoryDto saved= categoryDtoMapper.convert(categoryService.createCategory(categoryDto));
       CategoryDto saved= CategoryMapper.convertToDto(categoryService.createCategory(categoryDto));
        log.info(saved.getName());
        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create Category successfully",saved),HttpStatus.CREATED);
//        return new ResponseEntity<>(new Response(true, StatusCode.CREATED,"Create Category successfully",saved), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Response> findAllCategory(){
        List<CategoryDto> list =categoryService.findAllCategory().stream()
                .map(CategoryMapper::convertToDto)
                .toList();
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find all Category successfully",list), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> findCategoryById(@PathVariable Long id){
        CategoryDto categoryDto=CategoryMapper.convertToDto(categoryService.findCategoryById(id));
        return new ResponseEntity<>(new Response(true, StatusCode.SUCCESS,"Find one Category successfully",categoryDto), HttpStatus.OK);
    }
}
