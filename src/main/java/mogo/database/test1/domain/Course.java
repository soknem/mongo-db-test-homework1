package mogo.database.test1.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.BackgroundPreinitializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    private String title;

    private String slug;

    private Double price;

    private Double discount;

    private Boolean isPaid;

    private Boolean isDrafted;

    private String thumbnail;

    private String instructorName;

    private String description;

    private String categoryName;

    private String content;

    private Boolean isDeleted;

    private LocalDateTime updatedAt;

    List<Section> sectionList;

}
