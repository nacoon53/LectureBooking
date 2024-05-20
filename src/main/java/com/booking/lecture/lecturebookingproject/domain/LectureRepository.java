package com.booking.lecture.lecturebookingproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT lec.* " +
            "FROM (SELECT employee_id, application_status, lecture_id, created_at, " +
            "ROW_NUMBER() OVER (PARTITION BY lecture_id ORDER BY created_at DESC) AS rn " +
            "FROM application " +
            "WHERE employee_id = :employeeId) a, lecture lec " +
            "WHERE a.rn = 1 AND a.application_status = 1 AND a.lecture_id = lec.lecture_id GROUP BY lec.lecture_id",
            nativeQuery = true)
    List<Lecture> findLectureListByEmployeeId(@Param("employeeId")String employeeId);

    @Query(value = "SELECT lec.* FROM ( " +
            "SELECT employee_id, application_status, lecture_id, created_at, " +
            "ROW_NUMBER() OVER (PARTITION BY lecture_id, employee_id ORDER BY created_at DESC) AS rn " +
            "FROM application " +
            "WHERE created_at >= now() - INTERVAL 3 DAY) a, lecture lec " +
            "WHERE rn = 1 AND application_status = 1 AND a.lecture_id = lec.lecture_id " +
            "GROUP BY a.lecture_id " +
            "ORDER BY COUNT(a.lecture_id) DESC", nativeQuery = true)
    List<Lecture> findPopularLectures();

}
