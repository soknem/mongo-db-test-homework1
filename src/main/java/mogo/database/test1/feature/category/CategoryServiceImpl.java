package mogo.database.test1.feature.category;

import lombok.RequiredArgsConstructor;
import mogo.database.test1.domain.Category;
import mogo.database.test1.feature.category.dto.CategoryPopularResponse;
import mogo.database.test1.feature.category.dto.CategoryRequest;
import mogo.database.test1.feature.category.dto.CategoryResponse;
import mogo.database.test1.feature.category.dto.CategoryUpdateRequest;
import mogo.database.test1.mapper.CategoryMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    private final  CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.fromCategoryRequest(categoryRequest);

        category.setIsDeleted(false);

        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found",id)));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(String id, CategoryUpdateRequest categoryUpdateRequest) {

        Category category =
                categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found",id)));

        categoryMapper.updateCategoryFromRequest(category,categoryUpdateRequest);

       categoryRepository.save(category);

       return categoryMapper.toResponse(category);
    }

    @Override
    public void disableCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found",id)));

        category.setIsDeleted(true);

        categoryRepository.save(category);
    }

    @Override
    public void enableCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found",id)));

        category.setIsDeleted(false);

        categoryRepository.save(category);

    }

    @Override
    public void deleteCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found",id)));

        categoryRepository.delete(category);

    }

    @Override
    public List<CategoryPopularResponse> getPopularCategories() {

        // Group courses by categoryName and count them
        GroupOperation groupByCategory = Aggregation.group("categoryId")
                .count().as("totalCourse");

        // Sort by the course count in descending order
        SortOperation sortByCourseCountDesc = Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalCourse"));

        // Lookup the category details from the categories collection
        LookupOperation lookupCategory = Aggregation.lookup("categories", "categoryId", "title", "category");

        // Project the fields we want in the output
        ProjectionOperation project = Aggregation.project()
                .and("category.icon").as("icon")
                .and("category.title").as("name")
                .and("totalCourse").as("totalCourse");

        // Create the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                groupByCategory,
                sortByCourseCountDesc,
                lookupCategory,
                Aggregation.unwind("category"),
                project
        );

        // Execute the aggregation

        AggregationResults<CategoryPopularResponse> result = mongoTemplate.aggregate(aggregation, "courses", CategoryPopularResponse.class);
        return result.getMappedResults();
    }
}
