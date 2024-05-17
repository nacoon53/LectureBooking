package com.booking.lecture.lecturebookingproject.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long application_id;

    @Column(name="lecture_id", nullable=false)
    private Long lectureId;

    @Column(name="employee_id", nullable=false)
    private String employeeId;

    @Column(name="application_status", nullable=false)
    private int applicationStatus;

    @CreatedDate
    @Column(name="creted_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Application(Long lectureId, String employeeId, int applicationStatus, LocalDateTime createdAt) {
        this.lectureId = lectureId;
        this.employeeId = employeeId;
        this.applicationStatus = applicationStatus;
        this.createdAt = createdAt;
    }
}
