package com.booking.lecture.lecturebookingproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findTopByEmployeeIdAndLectureIdOrderByCreatedAtDesc(String employeeId, Long lectureId);

    @Query(value = "SELECT a.employee_id FROM ( " +
            " SELECT employee_id, " +
            "        application_status, " +
            "        lecture_id, " +
            "        created_at, " +
            "        ROW_NUMBER() OVER (PARTITION BY employee_id ORDER BY created_at DESC) AS rn " +
            " FROM application " +
            " WHERE lecture_id = :lectureId " +
            ") a " +
            "WHERE a.rn = 1 " +
            "AND a.application_status = 1", nativeQuery = true)
    List<String> findApplicationsByLectureId(@Param("lectureId")long lectureId);
}
