package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final ApplicationRepository applicationRepository;

    public List<LectureResponseDto> findAllLectures() {
        List<Lecture> entityList = lectureRepository.findAll();
        List<LectureResponseDto> resultList = new ArrayList<>();

        for(Lecture lecture: entityList) {
            resultList.add(new LectureResponseDto(lecture));
        }
        return resultList;
    }

    @Transactional
    public LectureResponseDto createLecture(LectureRequestDto lecture) {
        return new LectureResponseDto(lectureRepository.save(lecture.toEntity()));
    }

    public List<LectureResponseDto> findPopularLectures() {
        List<Lecture> entityList = lectureRepository.findPopularLectures();
        List<LectureResponseDto> resultList = new ArrayList<>();

        for(Lecture lecture: entityList) {
            resultList.add(new LectureResponseDto(lecture));
        }
        return resultList;
    }


    public List<LectureResponseDto> findAvailableLectures() {
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now().plusDays(2);

        from = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0,0,0);
        to = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth(), 0,0,0);

        List<Lecture> entityList = lectureRepository.findByStartDateBetween(from, to);

        List<LectureResponseDto> resultList = new ArrayList<>();

        for(Lecture lecture: entityList) {
            resultList.add(new LectureResponseDto(lecture));
        }
        return resultList;
    }

    @Transactional
    public boolean canApply(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("해당 강연을 찾을 수 없습니다. ID: " + lectureId));
        List<String> currentApplications = applicationRepository.findApplicationsByLectureId(lectureId);
        return currentApplications.size() < lecture.getMaxAttendees();
    }


    public List<LectureResponseDto> findApplicationsByEmployeeId(String employeeId) {
        List<Lecture> entityList = lectureRepository.findLectureListByEmployeeId(employeeId);
        List<LectureResponseDto> resultList = new ArrayList<>();

        for(Lecture lecture: entityList) {
            resultList.add(new LectureResponseDto(lecture));
        }
        return resultList;
    }
}
