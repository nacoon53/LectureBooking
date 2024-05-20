package com.booking.lecture.lecturebookingproject.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ApplicationRepositoryTest {

    @Autowired
    public ApplicationRepository applicationRepository;

    @Autowired
    public LectureRepository lectureRepository;

    final Long lectureId = 1L;
    final String employeeId = "T0001";
    final int applicationStatus = 1;

    Lecture lecture = null;

    @BeforeEach
    public void setUp() {
        lecture = Lecture.builder()
                .presenter("강연자1")
                .location("강연장1")
                .maxAttendees(10)
                .startDate(LocalDateTime.now())
                .contents("강연내용1")
                .build();

        lectureRepository.save(lecture);

        applicationRepository.save(Application.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(applicationStatus)
                .createdAt(LocalDateTime.now())
                .build());

    }
    @AfterEach
    public void cleanup() {
        applicationRepository.deleteAll();
    }

    @Test
    @DisplayName("Application 테이블에서 전체 조회")
    public void selectApplicationTable() {
        List<Application> list = applicationRepository.findAll();

        Application obj = list.get(0);
        assertThat(list.size()).isEqualTo(1);
        assertThat(obj.getLectureId()).isEqualTo(lectureId);
        assertThat(obj.getEmployeeId()).isEqualTo(employeeId);
        assertThat(obj.getApplicationStatus()).isEqualTo(applicationStatus);

    }

    @Test
    @DisplayName("특정 직원의 특정 강연 신청 여부 조회")
    public void findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc() {
        Optional<Application> obj = applicationRepository.findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(employeeId, lectureId);

        assertThat(obj.get().getApplicationStatus()).isEqualTo(1);

        //강연 취소를 할 경우
        applicationRepository.save(Application.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(0)
                .createdAt(LocalDateTime.now())
                .build());

        obj = applicationRepository.findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(employeeId, lectureId);

        assertThat(obj.get().getApplicationStatus()).isEqualTo(0);

    }

    @Test
    @DisplayName("강연 별 신청한 사번 목록 조회")
    public void findApplicationsByLectureId() {
        List<String> list = applicationRepository.findApplicationsByLectureId(lectureId);

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0)).isEqualTo(employeeId);

        //한 명이 더 신청하면
        String employeeId2 = "T0002";
        applicationRepository.save(Application.builder()
                .lectureId(lectureId)
                .employeeId("T0002")
                .applicationStatus(1)
                .createdAt(LocalDateTime.now())
                .build());

        list = applicationRepository.findApplicationsByLectureId(lectureId);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(employeeId);
        assertThat(list.get(1)).isEqualTo(employeeId2);

    }




}
