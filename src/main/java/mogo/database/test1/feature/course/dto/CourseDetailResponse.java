package mogo.database.test1.feature.course.dto;

import mogo.database.test1.feature.section.dto.SectionDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CourseDetailResponse(

        String id,

        String title,

        String slug,

        Double price,

        Double discount,

        Boolean isPaid,

        Boolean isDrafted,

        String thumbnail,

        String instructorName,

        String description,

        String categoryName,

        String content,

        LocalDateTime updatedAt,

        List<SectionDetailResponse> sections

) implements CourseResponse{
}
