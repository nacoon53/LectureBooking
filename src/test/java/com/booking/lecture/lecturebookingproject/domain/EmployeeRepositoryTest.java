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
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    public void cleanup() {
        employeeRepository.deleteAll();
    }

    @Test
    public void selectEmployeeTable() {
        String userId = "T0001";
        String name = "나경_테스트";
        LocalDateTime hireDate = LocalDateTime.of(2024, 5, 17, 0, 0, 0);
        boolean emplyedFlag = true;

        employeeRepository.save(Employee.builder()
                .employeeId(userId)
                .name(name)
                .hireDate(hireDate)
                .emplyedFlag(emplyedFlag)
                .build());

        List<Employee> usersList = employeeRepository.findAll();

        Employee user = usersList.get(0);
        assertThat(user.getEmployeeId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getHireDate()).isEqualTo(hireDate);
        assertThat(user.isEmployedFlag()).isEqualTo(emplyedFlag);
    }
}
