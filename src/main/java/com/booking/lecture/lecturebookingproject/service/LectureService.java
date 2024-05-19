package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Lecture createLecture(LectureRequestDto lecture) {
        return lectureRepository.save(lecture.toEntity());
    }
}
