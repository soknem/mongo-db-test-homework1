package mogo.database.test1.feature.course.dto;

import mogo.database.test1.feature.section.dto.SectionDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CourseRequest(

        String title,

        String slug,

        Double price,

        Double discount,

        Boolean isPaid,

        String thumbnail,

        String instructorName,

        String description,

        String categoryName,

        String content
) {
}
