package com.booking.lecture.lecturebookingproject.web.dto;

import com.booking.lecture.lecturebookingproject.domain.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationRequestDto {
    private Long lectureId;
    private String employeeId;
    private int applicationStatus;

    @Builder
    public ApplicationRequestDto(Long lectureId, String employeeId, int applicationStatus) {
        this.lectureId = lectureId;
        this.employeeId = employeeId;
        this.applicationStatus = applicationStatus;
    }

    public Application toEntity() {
        return Application.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(applicationStatus)
                .build();
    }
}