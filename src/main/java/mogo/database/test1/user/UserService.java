package mogo.database.test1.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);

    Optional<User> getUserById(String id);

    User createUser(User user);

    User updateUser(String id, User user);

    void deleteUser(String id);
}