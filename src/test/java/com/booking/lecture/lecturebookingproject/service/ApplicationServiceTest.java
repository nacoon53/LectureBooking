package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.Application;
import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationResponseDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @InjectMocks
    ApplicationService applicationService;

    @Mock
    LectureService lectureService;

    @Mock
    ApplicationRepository applicationRepository;

    @Test
    @DisplayName("강연 별 신청자 목록")
    void findApplicationsByLectureId() {
        long lectureId = 9999L;

        //given
        List<String> givenList = new ArrayList<>();
        givenList.add("T0001");

        when(applicationRepository.findApplicationsByLectureId(lectureId)).thenReturn(givenList);

        //when
        List<String> resultList = applicationService.findApplicationsByLectureId(lectureId);

        //then
        assertThat(resultList.size()).isEqualTo(1);
        verify(applicationRepository, times(1)).findApplicationsByLectureId(lectureId);
    }

    @Test
    @DisplayName("수강 신청 하기")
    void applyForLecture() {
        long lectureId = 9999L;
        String employeeId = "T0001";
        //given
        ApplicationRequestDto req = ApplicationRequestDto.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(1)
                .build();

        when(lectureService.canApply(lectureId)).thenReturn(true);
        when(applicationRepository.save(any(Application.class))).thenReturn(req.toEntity());

        //when
        ApplicationResponseDto res = applicationService.applyForLecture(req);

        //then
        assertThat(res).isNotNull();
        verify(lectureService).canApply(lectureId);
        verify(applicationRepository).save(any());
    }

    @Test
    @DisplayName("수강 취소 하기")
    void cancelApplication() {
        long lectureId = 9999L;
        String employeeId = "T0001";
        
        //given
        ApplicationRequestDto req = ApplicationRequestDto.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(0)
                .build();

        Optional<Application> obj = Optional.of(Application.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(1)
                .build());

        when(applicationRepository.findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(employeeId, lectureId)).thenReturn(obj);
        when(applicationRepository.save(any(Application.class))).thenReturn(req.toEntity());

        //when
        ApplicationResponseDto res = applicationService.cancelApplication(req);

        //then
        assertThat(res).isNotNull();
        verify(applicationRepository).findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(employeeId, lectureId);
        verify(applicationRepository).save(any());
    }

}
