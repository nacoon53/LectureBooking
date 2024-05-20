package com.booking.lecture.lecturebookingproject.web.admin;

import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.service.ApplicationService;
import com.booking.lecture.lecturebookingproject.service.LectureService;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/lectures")
public class  LectureController{
    private final LectureService lectureService;
    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<LectureResponseDto>> getAllLectures() { //강연 목록(전체 강연 목록)
        return ResponseEntity.ok(lectureService.findAllLectures());
    }

    @PostMapping
    public ResponseEntity<LectureResponseDto>createLecture(@RequestBody LectureRequestDto lecture) { //강연 등록(강연자, 강연장, 신청 인원, 강연 시간, 강연 내용 입력)
        return ResponseEntity.ok(lectureService.createLecture(lecture));
    }

    @GetMapping("/{lectureId}/applications")
    public ResponseEntity<List<String>> getApplicationsByLectureId(@PathVariable("lectureId") long lectureId) { //강연 신청자 목록(강연 별 신청한 사번 목록)
        List<String> applications = applicationService.findApplicationsByLectureId(lectureId);
        return ResponseEntity.ok(applications);
    }
}
