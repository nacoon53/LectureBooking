package com.booking.lecture.lecturebookingproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
