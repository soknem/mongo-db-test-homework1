package mogo.database.test1.feature.category.dto;

public record CategoryUpdateRequest(
        String title,

        String icon,

        String description
) {
}
