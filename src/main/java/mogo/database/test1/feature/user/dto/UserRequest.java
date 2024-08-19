package mogo.database.test1.feature.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserRequest(

        @NotBlank(message = "name is require")
        String name,

        @NotBlank(message = "email is require")
        String email,

        @NotNull(message = "age is require")
        @Positive(message = "age is positive")
        Integer age
) {
}
