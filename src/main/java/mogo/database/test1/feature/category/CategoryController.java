package mogo.database.test1.feature.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mogo.database.test1.feature.category.dto.CategoryPopularResponse;
import mogo.database.test1.feature.category.dto.CategoryRequest;
import mogo.database.test1.feature.category.dto.CategoryResponse;
import mogo.database.test1.feature.category.dto.CategoryUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(){

        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable String id){

        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategoryById(@PathVariable String id,
                                               @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return  categoryService.updateCategoryById(id,categoryUpdateRequest);
    }

    @PutMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableCategoryById(@PathVariable String id){

        categoryService.disableCategoryById(id);
    }

    @PutMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableCategoryById(@PathVariable String id){

        categoryService.enableCategoryById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable String id){

        categoryService.deleteCategoryById(id);
    }


    @GetMapping("/popular")
    public List<CategoryPopularResponse> getAllPopularCategories(){

        return categoryService.getPopularCategories();
    }


}
