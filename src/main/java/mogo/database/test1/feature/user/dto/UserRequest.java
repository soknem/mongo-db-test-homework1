package mogo.database.test1.feature.user.dto;

public record UserRequest(

        String name,
        String email,
        Integer age
) {
}
