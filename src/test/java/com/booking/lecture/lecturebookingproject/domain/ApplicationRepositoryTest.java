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
public class ApplicationRepositoryTest {

    @Autowired
    ApplicationRepository applicationRepository;

    @AfterEach
    public void cleanup() {
        applicationRepository.deleteAll();
    }

    @Test
    public void selecApplicationTable() {
        Long lectureId = 1L;
        String employeeId = "0000T";
        int applicationStatus = 1;

        applicationRepository.save(Application.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(applicationStatus)
                .createdAt(LocalDateTime.now())
                .build());

        List<Application> list = applicationRepository.findAll();

        Application obj = list.get(0);
        assertThat(obj.getLectureId()).isEqualTo(lectureId);
        assertThat(obj.getEmployeeId()).isEqualTo(employeeId);
        assertThat(obj.getApplicationStatus()).isEqualTo(applicationStatus);
    }
}
