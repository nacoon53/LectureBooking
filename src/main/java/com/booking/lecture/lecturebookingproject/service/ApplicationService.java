package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.Application;
import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationResponseDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;

    public List<String> findApplicationsByLectureId(long lectureId) {
        return applicationRepository.findApplicationsByLectureId(lectureId);
    }

    public ApplicationResponseDto applyForLecture(ApplicationRequestDto req) {
        if(!lectureService.canApply(req.getLectureId())) {
            throw new RuntimeException("허용 가능한 인원수를 초과 하였습니다.");
        }

        Optional<Application> obj = applicationRepository.findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(req.getEmployeeId(), req.getLectureId());

        if(obj.isPresent() && obj.get().getApplicationStatus() == 1) {
            throw new RuntimeException("중복 신청은 불가합니다.");
        }

        return new ApplicationResponseDto(applicationRepository.save(req.toEntity()));
    }

    public ApplicationResponseDto cancelApplication(ApplicationRequestDto req) {
        Optional<Application> obj = applicationRepository.findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(req.getEmployeeId(), req.getLectureId());

        if(obj.isEmpty() || obj.get().getApplicationStatus() == 0) {
            throw new RuntimeException("해당 ID로 신청된 내역이 없습니다.");
        }

        return new ApplicationResponseDto(applicationRepository.save(req.toEntity()));
    }

}
