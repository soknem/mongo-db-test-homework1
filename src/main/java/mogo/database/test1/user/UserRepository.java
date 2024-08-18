package mogo.database.test1.user;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // Custom query methods can be defined here if needed
}
