package com.booking.lecture.lecturebookingproject.web.dto;

import com.booking.lecture.lecturebookingproject.domain.Lecture;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LectureRequestDto {
    private Long lectureId;
    private String presenter;
    private String location;
    private int maxAttendees;
    private LocalDateTime startTime;
    private String contents;

    @Builder
    public LectureRequestDto(String presenter, String location, int maxAttendees, LocalDateTime startTime, String contents) {
        this.presenter = presenter;
        this.location = location;
        this.maxAttendees = maxAttendees;
        this.startTime = startTime;
        this.contents = contents;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .presenter(presenter)
                .location(location)
                .maxAttendees(maxAttendees)
                .startTime(startTime)
                .contents(contents)
                .build();
    }
}
