package mogo.database.test1.feature.video.dto;

public record VideoRequest(
        String title,

        String fileName,

        Integer orderNo,

        Integer sectionOrderNo
) {
}
