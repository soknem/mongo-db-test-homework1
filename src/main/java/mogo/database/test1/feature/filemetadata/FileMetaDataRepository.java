package mogo.database.test1.feature.filemetadata;

import mogo.database.test1.domain.FileMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileMetaDataRepository extends MongoRepository<FileMetaData, Long> {

    Optional<FileMetaData> findByFileName(String fileName);

    boolean existsByFileName(String fileName);
}
