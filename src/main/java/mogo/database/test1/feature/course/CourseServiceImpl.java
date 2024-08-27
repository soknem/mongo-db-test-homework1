package mogo.database.test1.feature.course;

import lombok.RequiredArgsConstructor;
import mogo.database.test1.base.BaseFilter;
import mogo.database.test1.base.BaseParamFilter;
import mogo.database.test1.domain.Course;
import mogo.database.test1.domain.Section;
import mogo.database.test1.domain.Video;
import mogo.database.test1.feature.course.dto.*;
import mogo.database.test1.feature.section.SectionRepository;
import mogo.database.test1.feature.section.dto.SectionDetailResponse;
import mogo.database.test1.feature.section.dto.SectionRequest;
import mogo.database.test1.feature.section.dto.SectionResponse;
import mogo.database.test1.feature.video.dto.VideoRequest;
import mogo.database.test1.feature.video.dto.VideoResponse;
import mogo.database.test1.feature.video.dto.VideoUpdateRequest;
import mogo.database.test1.mapper.CourseMapper;
import mogo.database.test1.mapper.SectionMapper;
import mogo.database.test1.mapper.VideoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final SectionMapper sectionMapper;

    private final VideoMapper videoMapper;

    private final CourserRepository courserRepository;

    private final SectionRepository sectionRepository;

    private final BaseFilter<Course> courseBaseFilter;

    private final BaseParamFilter<Course> courseBaseParamFilter;

    private final MongoTemplate mongoTemplate;


    @Override
    public void creatCourse(CourseRequest courseRequest) {

        Course course = courseMapper.fromRequest(courseRequest);

        course.setIsDrafted(true);

        course.setIsDeleted(false);

        courserRepository.save(course);
    }

    @Override
    public Page<CourseResponse> getAllCourses(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courserRepository.findAll(pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });

        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }

    }

    @Override
    public CourseResponse getCourseById(String responseType, String id) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        if (responseType.equalsIgnoreCase("content_details")) {

            List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

            return courseMapper.toCourseDetailResponse(course, sections);

        } else {
            return courseMapper.toCourseSnippetResponse(course);
        }
    }

    @Override
    public CourseResponse updateCourseById(String responseType, String id, CourseUpdateRequest courseUpdateRequest) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        courseMapper.updateCourseFromRequest(course, courseUpdateRequest);

        courserRepository.save(course);

        if (responseType.equalsIgnoreCase("content_details")) {

            List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

            return courseMapper.toCourseDetailResponse(course, sections);

        } else {
            return courseMapper.toCourseSnippetResponse(course);
        }
    }

    @Override
    public void updateCourseVisibilityById(String id, CourseVisibilityUpdateRequest courseVisibilityUpdateRequest) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsDrafted(courseVisibilityUpdateRequest.isDrafted());

        courserRepository.save(course);

    }

    @Override
    public void updateCourseThumbnailById(String id, CourseThumbnailUpdateRequest courseThumbnailUpdateRequest) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setThumbnail(courseThumbnailUpdateRequest.thumbnail());

        courserRepository.save(course);


    }

    @Override
    public void updateCourseIsPaidById(String id, CoursePaidUpdateRequest coursePaidUpdateRequest) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsPaid(coursePaidUpdateRequest.isPaid());

        courserRepository.save(course);
    }

    @Override
    public void enableCourseById(String id) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsDeleted(false);

        courserRepository.save(course);
    }

    @Override
    public void disableCourseById(String id) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsDeleted(true);

        courserRepository.save(course);

    }

    @Override
    public void deleteCourseById(String id) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        courserRepository.delete(course);
    }

    @Override
    public void crateVideo(String id, VideoRequest videoRequest) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        Section section =
                sectionRepository.findByOrderNoAndCourseId(videoRequest.sectionOrderNo(), course.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("section with orderNo =  %s has not been found", videoRequest.sectionOrderNo())));

        Video video = videoMapper.fromRequest(videoRequest);

        section.getVideos().add(video);

        sectionRepository.save(section);
    }

    @Override
    public VideoResponse updateVideo(String id, VideoUpdateRequest videoUpdateRequest) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        Section section =
                sectionRepository.findByOrderNoAndCourseId(videoUpdateRequest.sectionOrderNo(),
                        course.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("section with orderNo =  %s and courseId = %s has not been found", videoUpdateRequest.sectionOrderNo(), course.getTitle())));

        // Find and update the video in the section
        Video video = section.getVideos().stream()
                .filter(vid -> vid.getOrderNo().equals(videoUpdateRequest.orderNo()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("video with order = %s in section = %s of course =%s has not been found",
                                videoUpdateRequest.orderNo(), section.getId(), course.getId())));

        videoMapper.updateVideoFromRequest(video, videoUpdateRequest);

        sectionRepository.save(section);

        return videoMapper.toResponse(video);
    }

    @Override
    public List<SectionResponse> getAllSections(String id) {

        Course course =
                courserRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course =  %s has not been found", id)));

        List<Section> sections = sectionRepository.findAllByCourseId(course.getId());

        return sections.stream().map(sectionMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void createSection(String courseId, SectionRequest sectionRequest) {

        Course course =
                courserRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("course  = %s has not been found", courseId)));

        Section section = sectionMapper.fromRequest(sectionRequest);


        sectionRepository.save(section);
    }

    @Override
    public Page<CourseResponse> filterCourseByBody(String responseType, BaseFilter.FilterDto filterDto, int pageNumber, int pageSize) {

        Query query = courseBaseFilter.buildQuery(filterDto, Course.class);

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        query.with(pageRequest);

        Page<Course> coursePage = (Page<Course>) mongoTemplate.find(query, Course.class);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> filterCourseByParam(String responseType, WebRequest webRequest, int pageNumber, int pageSize) {

        Query query = courseBaseParamFilter.buildQuery(webRequest, Course.class);

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        query.with(pageRequest);

        Page<Course> coursePage = (Page<Course>) mongoTemplate.find(query, Course.class);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public CourseResponse getCourseBySlug(String responseType, String slug) {

        Course course =
                courserRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course with slug =  %s has not been found", slug)));

        if (responseType.equalsIgnoreCase("content_details")) {

            List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

            return courseMapper.toCourseDetailResponse(course, sections);
        } else {
            return courseMapper.toCourseSnippetResponse(course);
        }
    }

    @Override
    public Page<CourseResponse> getPublicCourses(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courserRepository.findAllByIsDraftedFalse(pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> getPrivateCourses(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courserRepository.findAllByIsDraftedTrue(pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> getCourseByInstructorName(String responseType, String instructorName, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courserRepository.findAllByInstructorName(instructorName, pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> getFreeCourse(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courserRepository.findAllByPriceLessThanEqual(0.0, pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map((course) -> {

                List<SectionDetailResponse> sections = sectionRepository.findAllByCourseId(course.getId()).stream().map(sectionMapper::toSectionDetailResponse).toList();

                return courseMapper.toCourseDetailResponse(course, sections);
            });
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }
}
