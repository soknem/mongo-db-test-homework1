package mogo.database.test1.feature.section;

import mogo.database.test1.domain.Section;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends MongoRepository<Section,String> {

    List<Section> findAllByCourseId(String courseId);

    Optional<Section> findByOrderNoAndCourseId(Integer orderNo,String courseId);

}
