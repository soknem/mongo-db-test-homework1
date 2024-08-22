package mogo.database.test1.feature.category.dto;

public record CategoryResponse(

        String id,
        String title,

        String icon,

        String description,

        Boolean isDeleted
) {
}
