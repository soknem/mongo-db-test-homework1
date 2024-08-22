package mogo.database.test1.feature.category;

import mogo.database.test1.feature.category.dto.CategoryPopularResponse;
import mogo.database.test1.feature.category.dto.CategoryRequest;
import mogo.database.test1.feature.category.dto.CategoryResponse;
import mogo.database.test1.feature.category.dto.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(String id);

    CategoryResponse updateCategoryById(String id, CategoryUpdateRequest categoryUpdateRequest);

    void disableCategoryById(String id);

    void enableCategoryById(String id);

    void deleteCategoryById(String id);

    List<CategoryPopularResponse> getPopularCategories();
}
