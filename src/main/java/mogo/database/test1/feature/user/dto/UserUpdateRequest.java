package mogo.database.test1.feature.user.dto;

import jakarta.validation.constraints.Positive;

public record UserUpdateRequest(
        String name,
        String email,
        @Positive(message = "age is positive")
        Integer age
) {
}
