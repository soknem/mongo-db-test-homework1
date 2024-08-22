package mogo.database.test1.mapper;

import mogo.database.test1.domain.Category;
import mogo.database.test1.domain.Course;
import mogo.database.test1.feature.category.dto.CategoryUpdateRequest;
import mogo.database.test1.feature.course.dto.*;
import mogo.database.test1.feature.section.dto.SectionDetailResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper{

    Course fromRequest(CourseRequest courseRequest);

    CourseSnippetResponse toCourseSnippetResponse(Course course);

    CourseDetailResponse toCourseDetailResponse(Course course, List<SectionDetailResponse> sections);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFromRequest(@MappingTarget Course course, CourseUpdateRequest courseUpdateRequest);

}
