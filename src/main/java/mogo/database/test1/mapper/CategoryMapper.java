package mogo.database.test1.mapper;

import mogo.database.test1.domain.Category;
import mogo.database.test1.domain.User;
import mogo.database.test1.feature.category.dto.CategoryRequest;
import mogo.database.test1.feature.category.dto.CategoryResponse;
import mogo.database.test1.feature.category.dto.CategoryUpdateRequest;
import mogo.database.test1.feature.user.dto.UserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category fromCategoryRequest(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromRequest(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);

}
