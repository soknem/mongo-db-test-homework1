package mogo.database.test1.feature.course.dto;

public record CourseUpdateRequest(

        String title,

        String slug,

        Double price,

        Double discount,

        String instructorName,

        String description,

        String categoryName,

        String content
) {
}
