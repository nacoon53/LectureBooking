package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LectureServiceTest {
    @InjectMocks
    LectureService lectureService;

    @Mock
    LectureRepository lectureRepository;

    @Test
    void findAllLectures() {
        //given
        List<Lecture> lectures = new ArrayList<>();
        Lecture lecture = Lecture.builder()
                .presenter("강연자1")
                .location("강연위치1")
                .maxAttendees(10)
                .startTime(LocalDateTime.now())
                .contents("강연내용1")
                .build();

        Lecture lecture2 = Lecture.builder()
                .presenter("강연자2")
                .location("강연위치2")
                .maxAttendees(20)
                .startTime(LocalDateTime.now())
                .contents("강연내용2")
                .build();

        lectures.add(lecture);
        lectures.add(lecture2);

        when(lectureRepository.findAll()).thenReturn(lectures);

        //when
        List<LectureResponseDto> result = lectureService.findAllLectures();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void craeteLecture() {
        //given
        String presenter = "나경_테스트";
        String location = "서울시 영등포구";
        int maxAttendees = 30;
        LocalDateTime startTime = LocalDateTime.of(2024,06,03, 10,30, 0);
        String startTimeStr = startTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String contents = "2024 트렌드";

        LectureRequestDto req = LectureRequestDto.builder()
                .presenter(presenter)
                .location(location)
                .maxAttendees(maxAttendees)
                .startTime(startTime)
                .contents(contents)
                .build();

        //when
        lectureService.createLecture(req);

        //then
        verify(lectureRepository).save(any());
    }
}
