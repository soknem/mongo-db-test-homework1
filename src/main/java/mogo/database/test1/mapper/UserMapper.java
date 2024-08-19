package mogo.database.test1.mapper;

import mogo.database.test1.domain.User;
import mogo.database.test1.feature.user.dto.UserRequest;
import mogo.database.test1.feature.user.dto.UserResponse;
import mogo.database.test1.feature.user.dto.UserUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromRequest(UserRequest userRequest);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(@MappingTarget User user, UserUpdateRequest userRequest);


}
