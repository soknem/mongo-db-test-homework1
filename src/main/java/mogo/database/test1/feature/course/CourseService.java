package mogo.database.test1.feature.course;

import mogo.database.test1.base.BaseFilter;
import mogo.database.test1.feature.course.dto.*;
import mogo.database.test1.feature.section.dto.SectionResponse;
import mogo.database.test1.feature.video.dto.VideoRequest;
import mogo.database.test1.feature.video.dto.VideoResponse;
import mogo.database.test1.feature.video.dto.VideoUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

public interface CourseService {

    void creatCourse(CourseRequest courseRequest);

    Page<CourseResponse> getAllCourses(String responseType,int pageNumber, int pageSize);

    CourseResponse getCourseById(String responseType,String id);

    CourseResponse updateCourseById(String responseType,String id, CourseUpdateRequest courseUpdateRequest);

    void updateCourseVisibilityById(String id, CourseVisibilityUpdateRequest courseVisibilityUpdateRequest);

    void updateCourseThumbnailById(String id, CourseThumbnailUpdateRequest courseThumbnailUpdateRequest);

    void updateCourseIsPaidById(String id,CoursePaidUpdateRequest coursePaidUpdateRequest);

    void enableCourseById(String id);

    void disableCourseById(String id);

    void deleteCourseById(String id);

    void crateVideo(String id, VideoRequest videoRequest);

    VideoResponse updateVideo(String id, VideoUpdateRequest videoUpdateRequest);

    List<SectionResponse> getAllSections(String id);

    Page<CourseResponse> filterCourseByBody(String responseType,BaseFilter.FilterDto filterDto,int pageNumber,
                                            int pageSize);

    Page<CourseResponse> filterCourseByParam(String responseType,WebRequest webRequest,int pageNumber, int pageSize);

    CourseResponse getCourseBySlug(String responseType,String slug);

    Page<CourseResponse> getPublicCourses(String responseType,int pageNumber, int pageSize);

    Page<CourseResponse> getPrivateCourses(String responseType,int pageNumber, int pageSize);

    Page<CourseResponse> getCourseByInstructorName(String responseType,String instructorName,int pageNumber, int pageSize);

    Page<CourseResponse> getFreeCourse(String responseType,int pageNumber, int pageSize);

}
