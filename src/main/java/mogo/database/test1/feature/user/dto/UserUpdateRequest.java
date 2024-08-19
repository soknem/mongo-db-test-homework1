package mogo.database.test1.feature.user.dto;

public record UserUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
