package com.booking.lecture.lecturebookingproject.domain;

import com.booking.lecture.lecturebookingproject.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Lecture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String presenter;

    @Column(nullable = false)
    private String location;

    @Column(name="max_attendees")
    private int maxAttendees;

    @Column(name="start_time")
    private LocalDateTime startTime;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Lecture(String presenter, String location, int maxAttendees, LocalDateTime startTime, String contents) {
        this.presenter = presenter;
        this.location = location;
        this.maxAttendees = maxAttendees;
        this.startTime = startTime;
        this.contents = contents;
    }
}
