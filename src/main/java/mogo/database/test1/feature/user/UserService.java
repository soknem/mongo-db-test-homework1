package mogo.database.test1.feature.user;

import mogo.database.test1.base.BaseFilter;
import mogo.database.test1.domain.User;
import mogo.database.test1.feature.user.dto.UserRequest;
import mogo.database.test1.feature.user.dto.UserResponse;
import mogo.database.test1.feature.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserResponse> getAllUsers(int pageNumber,int pageSize);

    UserResponse getUserByUuid(String uuid);

    void createUser(UserRequest userRequest);

    UserResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest);

    void deleteUser(String uuid);

    List<UserResponse> filterUsers(BaseFilter.FilterDto filterDto);
}