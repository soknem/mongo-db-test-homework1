package mogo.database.test1.feature.section.dto;

import mogo.database.test1.domain.Video;

import java.util.List;

public record SectionResponse(
        String id,

        String title,

        Integer orderNo,

        String courseId
        ) {
}
