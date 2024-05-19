package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationServiceTest {
    @InjectMocks
    ApplicationService applicationService;

    @Mock
    ApplicationRepository applicationRepository;

    @Test
    void findApplicationsByLectureId() {

    }
}
