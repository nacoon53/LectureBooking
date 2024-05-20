package com.booking.lecture.lecturebookingproject.web.front;

import com.booking.lecture.lecturebookingproject.service.ApplicationService;
import com.booking.lecture.lecturebookingproject.service.LectureService;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationResponseDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final LectureService lectureService;
    private final ApplicationService applicationService;

    //신청 가능한 강연 목록 또는 인기 강연 조회
    @GetMapping("/lectures")
    public ResponseEntity<List<LectureResponseDto>> getLectures(@RequestParam(name="popular", defaultValue = "false") boolean popular) {
        if(popular) {
            return ResponseEntity.ok(lectureService.findPopularLectures());
        }
        return ResponseEntity.ok(lectureService.findAvailableLectures());
    }

    //강연 신청
    @PostMapping("/lectures/apply")
    public ResponseEntity<ApplicationResponseDto> applyForLecture(@RequestBody ApplicationRequestDto req) {
        return ResponseEntity.ok(applicationService.applyForLecture(req));
    }

    //강연 취소
    @PostMapping("/lectures/cancel")
    public ResponseEntity<ApplicationResponseDto> cancelApplication(@RequestBody ApplicationRequestDto req) {
        return ResponseEntity.ok(applicationService.cancelApplication(req));
    }

    //신청한 강연 조회
    @GetMapping("/users/{employeeId}/applications")
    public ResponseEntity<List<LectureResponseDto>> getApplicationsByEmployeeId(@PathVariable("employeeId") String employeeId) {
        return ResponseEntity.ok(lectureService.findApplicationsByEmployeeId(employeeId));
    }
}
