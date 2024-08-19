package mogo.database.test1.feature.user.dto;

public record UserResponse(
        String uuid,
        String name,
        String email,
        Integer age
) {
}
