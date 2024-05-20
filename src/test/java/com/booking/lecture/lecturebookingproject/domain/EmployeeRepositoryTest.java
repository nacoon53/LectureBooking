package com.booking.lecture.lecturebookingproject.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    public void cleanup() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Employee 테이블에서 전체 조회")
    public void selectEmployeeTable() {
        //given
        String userId = "T0001";
        String name = "나경_테스트";
        LocalDateTime hireDate = LocalDateTime.of(2024, 5, 17, 0, 0, 0);
        boolean employedFlag = true;

        employeeRepository.save(Employee.builder()
                .employeeId(userId)
                .name(name)
                .hireDate(hireDate)
                .employedFlag(employedFlag)
                .build());

        //when
        List<Employee> usersList = employeeRepository.findAll();

        //then
        Employee user = usersList.get(0);
        assertThat(usersList.size()).isEqualTo(1);
        assertThat(user.getEmployeeId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getHireDate()).isEqualTo(hireDate);
        assertThat(user.isEmployedFlag()).isEqualTo(employedFlag);
    }
}
