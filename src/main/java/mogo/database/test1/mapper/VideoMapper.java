package mogo.database.test1.mapper;

import mogo.database.test1.domain.Category;
import mogo.database.test1.domain.Video;
import mogo.database.test1.feature.category.dto.CategoryUpdateRequest;
import mogo.database.test1.feature.video.dto.VideoRequest;
import mogo.database.test1.feature.video.dto.VideoResponse;
import mogo.database.test1.feature.video.dto.VideoUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    Video fromRequest(VideoRequest videoRequest);

    VideoResponse toResponse(Video video);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVideoFromRequest(@MappingTarget Video Video, VideoUpdateRequest videoUpdateRequest);

}
