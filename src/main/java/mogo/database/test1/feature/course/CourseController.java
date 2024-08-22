package mogo.database.test1.feature.course;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mogo.database.test1.base.BaseFilter;
import mogo.database.test1.feature.course.dto.*;
import mogo.database.test1.feature.section.dto.SectionRequest;
import mogo.database.test1.feature.section.dto.SectionResponse;
import mogo.database.test1.feature.video.dto.VideoRequest;
import mogo.database.test1.feature.video.dto.VideoResponse;
import mogo.database.test1.feature.video.dto.VideoUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@Valid @RequestBody CourseRequest courseRequest) {

        courseService.creatCourse(courseRequest);
    }

    @GetMapping("")
    public Page<CourseResponse> getAllCourses(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {


        return courseService.getAllCourses(responseType, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @PathVariable String id) {

        return courseService.getCourseById(responseType,id);
    }

    @PutMapping("/{id}")
    public CourseResponse updateCourseById(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @PathVariable String id,
            @Valid @RequestBody CourseUpdateRequest courseUpdateRequest) {

        return courseService.updateCourseById(responseType,id, courseUpdateRequest);

    }


    @PutMapping("/{id}/visibilities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourseVisibilityById(
            @PathVariable String id,
            @Valid @RequestBody CourseVisibilityUpdateRequest courseVisibilityUpdateRequest) {

        courseService.updateCourseVisibilityById(id, courseVisibilityUpdateRequest);
    }

    @PutMapping("/{id}/thumbnail")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourseThumbnailById
            (@PathVariable String id,
             @Valid @RequestBody CourseThumbnailUpdateRequest courseThumbnailUpdateRequest) {

        courseService.updateCourseThumbnailById(id, courseThumbnailUpdateRequest);
    }


    @PutMapping("/{id}/is-paid")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourseIsPaidById(@PathVariable String id,
                                       @Valid @RequestBody CoursePaidUpdateRequest coursePaidUpdateRequest) {

        courseService.updateCourseIsPaidById(id, coursePaidUpdateRequest);
    }

    @PutMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableCourseById(@PathVariable String id) {

        courseService.disableCourseById(id);
    }

    @PutMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableCourseById(@PathVariable String id) {

        courseService.enableCourseById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseById(@PathVariable String id) {

        courseService.deleteCourseById(id);
    }

    @PostMapping("/{courseId}/videos")
    public void createVideo(@PathVariable String courseId, VideoRequest videoRequest) {

        courseService.crateVideo(courseId, videoRequest);
    }


    @PutMapping("/{courseId}/videos")
    public VideoResponse updateVideo(@PathVariable String courseId, @Valid @RequestBody VideoUpdateRequest videoRequest) {
        return courseService.updateVideo(courseId, videoRequest);
    }

    @GetMapping("/{id}/sections")
    public List<SectionResponse> getAllSections(@PathVariable String id) {

        return courseService.getAllSections(id);
    }


    @PostMapping("/{courseId}/sections")
    public void createSection(@PathVariable String courseId, @RequestBody SectionRequest sectionRequest) {

        courseService.createSection(courseId,sectionRequest);
    }

    @GetMapping("/filter")
    public Page<CourseResponse> filterCourseByParams(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            WebRequest request,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize
    ) {

        return courseService.filterCourseByParam(responseType,request, pageNumber, pageSize);

    }

    @GetMapping("/filters")
    public Page<CourseResponse> filterCourseByBody(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @RequestBody BaseFilter.FilterDto filterDto,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {

        return courseService.filterCourseByBody(responseType,filterDto, pageNumber, pageSize);
    }

    @GetMapping("/slug/{slug}")
    public CourseResponse getCourseBySlug(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @PathVariable String slug) {

        return courseService.getCourseBySlug(responseType,slug);
    }

    @GetMapping("/public")
    public Page<CourseResponse> getPublicCourses(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize
    ) {

        return courseService.getPublicCourses(responseType,pageNumber, pageSize);
    }

    @GetMapping("/private")
    public Page<CourseResponse> getPrivateCourses(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize
    ) {
        return courseService.getPrivateCourses(responseType,pageNumber, pageSize);
    }

    @GetMapping("/instructor/{instructorName}")
    public Page<CourseResponse> getCoursesByInstructorName(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @PathVariable String instructorName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize) {

        return courseService.getCourseByInstructorName(responseType,instructorName, pageNumber, pageSize);
    }


    @GetMapping("/free")
    public Page<CourseResponse> getFreeCourse(
            @Parameter(
                    description = "Specify the type of response",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "25") int pageSize
    ) {
        return courseService.getFreeCourse(responseType,pageNumber, pageSize);
    }


}
