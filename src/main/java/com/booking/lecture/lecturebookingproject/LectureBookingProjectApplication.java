package com.booking.lecture.lecturebookingproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LectureBookingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LectureBookingProjectApplication.class, args);
	}

}
