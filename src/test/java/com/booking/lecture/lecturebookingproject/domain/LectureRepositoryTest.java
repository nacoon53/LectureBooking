package com.booking.lecture.lecturebookingproject.domain;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LectureRepositoryTest {

    @Autowired
    LectureRepository lectureRepository;

    @AfterEach
    public void cleanup() {
        lectureRepository.deleteAll();
    }

    @Test
    public void selecLectureTable() {
        String presenter = "나경";
        String location = "영등포구 당산동";
        int maxAttendees = 30;
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 3, 10, 30, 0);
        String contents = "2024 트렌드";

        lectureRepository.save(Lecture.builder()
                .presenter(presenter)
                .location(location)
                .maxAttendees(maxAttendees)
                .startTime(startTime)
                .contents(contents)
                .build());

        List<Lecture> list = lectureRepository.findAll();

        Lecture lecture = list.get(0);
        assertThat(lecture.getPresenter()).isEqualTo(presenter);
        assertThat(lecture.getLocation()).isEqualTo(location);
        assertThat(lecture.getMaxAttendees()).isEqualTo(maxAttendees);
        assertThat(lecture.getStartTime()).isEqualTo(startTime);
        assertThat(lecture.getContents()).isEqualTo(contents);
    }
}
