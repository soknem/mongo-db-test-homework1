package mogo.database.test1.mapper;

import mogo.database.test1.domain.Course;
import mogo.database.test1.domain.Section;
import mogo.database.test1.feature.course.dto.CourseUpdateRequest;
import mogo.database.test1.feature.section.dto.SectionRequest;
import mogo.database.test1.feature.section.dto.SectionResponse;
import mogo.database.test1.feature.section.dto.SectionUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    Section fromRequest(SectionRequest sectionRequest);

    SectionResponse toResponse(Section section);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSectionFromRequest(@MappingTarget Section section, SectionUpdateRequest sectionUpdateRequest);

}
