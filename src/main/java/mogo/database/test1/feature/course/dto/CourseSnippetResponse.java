package mogo.database.test1.feature.course.dto;

import java.time.LocalDateTime;

public record CourseSnippetResponse(

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

         LocalDateTime updatedAt

) implements CourseResponse{
}
