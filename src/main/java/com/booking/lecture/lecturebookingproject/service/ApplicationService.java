package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.Application;
import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private ApplicationRepository applicationRepository;

    public List<String> findApplicationsByLectureId(Long lectureId) {
        return applicationRepository.findApplicationsByLectureId(lectureId);
    }
}
