package mogo.database.test1.feature.user;

import lombok.RequiredArgsConstructor;
import mogo.database.test1.base.BaseFilter;
import mogo.database.test1.domain.User;
import mogo.database.test1.feature.user.dto.UserRequest;
import mogo.database.test1.feature.user.dto.UserResponse;
import mogo.database.test1.feature.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam int pageNumber,int pageSize) {
        return userService.getAllUsers(pageNumber,pageSize);
    }

    @GetMapping("/{uuid}")
    public UserResponse getUserByUuid(@PathVariable String uuid) {
        return userService.getUserByUuid(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequest user) {
        userService.createUser(user);
    }

    @PutMapping("/{uuid}")
    public UserResponse updateUser(@PathVariable String uuid, @RequestBody UserUpdateRequest user) {
        return userService.updateUser(uuid, user);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String uuid) {
        userService.deleteUser(uuid);
    }

    @PostMapping("/filters")
    public List<UserResponse> filterUsers(@RequestBody BaseFilter.FilterDto filterDto){
        return userService.filterUsers(filterDto);
    }
}