package mogo.database.test1.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "sections")
public class Section {

    @Id
    private String id;

    private String title;

    private Integer orderNo;

    private String courseId;

    private List<Video> videos;


}
