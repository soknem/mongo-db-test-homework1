package mogo.database.test1.feature.user;


import mogo.database.test1.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUuid(String uuid);
}
