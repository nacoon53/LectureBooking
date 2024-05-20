package com.booking.lecture.lecturebookingproject.web.dto;

import com.booking.lecture.lecturebookingproject.domain.Lecture;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LectureResponseDto {
    private Long lectureId;
    private String presenter;
    private String location;
    private int maxAttendees;
    private LocalDateTime startDate;
    private String contents;

    public LectureResponseDto(Lecture entity) {
        this.lectureId = entity.getLectureId();
        this.presenter = entity.getPresenter();
        this.location = entity.getLocation();
        this.maxAttendees = entity.getMaxAttendees();
        this.startDate = entity.getStartDate();
        this.contents  = entity.getContents();
    }
}
