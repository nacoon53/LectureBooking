package com.booking.lecture.lecturebookingproject.domain;

import com.booking.lecture.lecturebookingproject.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Lecture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @Column(nullable = false)
    private String presenter;

    @Column(nullable = false)
    private String location;

    @Column(name="max_attendees")
    private int maxAttendees;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Lecture(long lectureId, String presenter, String location, int maxAttendees, LocalDateTime startDate, String contents) {
        this.lectureId = lectureId;
        this.presenter = presenter;
        this.location = location;
        this.maxAttendees = maxAttendees;
        this.startDate = startDate;
        this.contents = contents;
    }
}
