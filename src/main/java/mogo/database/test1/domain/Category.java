package mogo.database.test1.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Inherited;

@Document(collection = "categories")
@Data
public class Category {
    @Id
    private String id;

    private String title;

    private String icon;

    private  String description;

    private Boolean isDeleted;
}
