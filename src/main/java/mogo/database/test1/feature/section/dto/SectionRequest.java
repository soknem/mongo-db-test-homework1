package mogo.database.test1.feature.section.dto;

import mogo.database.test1.domain.Video;

import java.util.List;

public record SectionRequest(

        String title,

        Integer orderNo,

        String courseId,

        List<Video> videos

        ) {
}
