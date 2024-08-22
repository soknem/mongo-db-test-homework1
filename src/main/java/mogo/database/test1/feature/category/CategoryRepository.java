package mogo.database.test1.feature.category;

import mogo.database.test1.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String > {
}
