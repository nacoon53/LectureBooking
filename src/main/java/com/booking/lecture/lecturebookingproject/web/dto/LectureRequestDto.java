package com.booking.lecture.lecturebookingproject.web.dto;

import com.booking.lecture.lecturebookingproject.domain.Lecture;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LectureRequestDto {
    private Long lectureId;
    private String presenter;
    private String location;
    private int maxAttendees;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    private String contents;

    @Builder
    public LectureRequestDto(String presenter, String location, int maxAttendees, LocalDateTime startDate, String contents) {
        this.presenter = presenter;
        this.location = location;
        this.maxAttendees = maxAttendees;
        this.startDate = startDate;
        this.contents = contents;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .presenter(presenter)
                .location(location)
                .maxAttendees(maxAttendees)
                .startDate(startDate)
                .contents(contents)
                .build();
    }
}
