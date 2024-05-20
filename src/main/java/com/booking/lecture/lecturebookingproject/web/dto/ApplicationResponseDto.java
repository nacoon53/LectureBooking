package com.booking.lecture.lecturebookingproject.web.dto;

import com.booking.lecture.lecturebookingproject.domain.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationResponseDto {
    private Long lectureId;
    private String employeeId;
    private int applicationStatus;

    public ApplicationResponseDto(Application entity) {
        this.lectureId = entity.getLectureId();
        this.employeeId = entity.getEmployeeId();
        this.applicationStatus = entity.getApplicationStatus();
    }
}