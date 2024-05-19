package com.booking.lecture.lecturebookingproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT DISTINCT a.employeeId FROM Application a WHERE a.lectureId = :lectureId AND a.applicationStatus = 1")
    List<String> findApplicationsByLectureId(Long lectureId);

}
