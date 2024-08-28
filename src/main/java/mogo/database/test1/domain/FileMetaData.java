package mogo.database.test1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "file_meta_data")
public class FileMetaData {
    @Id
    String id;

    String fileName;

    String contentType;

    String folder;

    Long fileSize;

    String extension;

}
