package com.booking.lecture.lecturebookingproject.domain;

import com.booking.lecture.lecturebookingproject.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Employee extends BaseTimeEntity {
    @Id
    @Column(name="employee_id", nullable=false, length=5)
    private String employeeId;

    @Column(nullable = false)
    private String name;

    @Column(name="hire_date", nullable=false)
    private LocalDateTime hireDate;

    @Column(name="employed_flag", nullable = false)
    private boolean employedFlag;

    @Builder
    public Employee(String employeeId, String name, LocalDateTime hireDate, boolean employedFlag) {
        this.employeeId = employeeId;
        this.name = name;
        this.hireDate = hireDate;
        this.employedFlag = employedFlag;
    }
}
