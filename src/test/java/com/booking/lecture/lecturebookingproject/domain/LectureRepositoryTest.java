package com.booking.lecture.lecturebookingproject.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LectureRepositoryTest {

    @Autowired
    public ApplicationRepository applicationRepository;

    @Autowired
    public LectureRepository lectureRepository;

    Lecture lecture = null;

    @BeforeEach
    public void setUp() {
        //LocalDateTime startDate = LocalDateTime.now();
        //String startDateStr = startDate.format(DateTimeFormatter.ISO_DATE_TIME);
        lecture = Lecture.builder()
                .presenter("강연자1")
                .location("강연장1")
                .maxAttendees(10)
                .startDate(LocalDateTime.now())
                .contents("강연내용1")
                .build();

        lectureRepository.save(lecture);
    }

    @AfterEach
    public void cleanup() {
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("Lecture 테이블에서 전체 조회")
    public void selectLectureTable() {
        //when
        List<Lecture> list = lectureRepository.findAll();

        //then
        Lecture lecture = list.get(0);
        assertThat(list.size()).isEqualTo(1);
        assertThat(lecture.getPresenter()).isEqualTo(lecture.getPresenter());
        assertThat(lecture.getLocation()).isEqualTo(lecture.getLocation());
        assertThat(lecture.getMaxAttendees()).isEqualTo(lecture.getMaxAttendees());
        assertThat(lecture.getStartDate()).isEqualTo(lecture.getStartDate());
        assertThat(lecture.getContents()).isEqualTo(lecture.getContents());
    }

    @Test
    @DisplayName("특정 기간 내 강연 조회하기")
    public void selectByStartDateBetween() {
        lectureRepository.save(Lecture.builder()
                .presenter("강연자2")
                .location("강연장2")
                .maxAttendees(20)
                .startDate( LocalDateTime.now().minusDays(10))
                .contents("강연내용2")
                .build());

        lectureRepository.save(Lecture.builder()
                .presenter("강연자3")
                .location("강연장3")
                .maxAttendees(30)
                .startDate( LocalDateTime.now().plusDays(2))
                .contents("강연내용3")
                .build());

        Lecture lec = lectureRepository.save(Lecture.builder()
                .presenter("강연자3")
                .location("강연장3")
                .maxAttendees(30)
                .startDate( LocalDateTime.now().plusDays(1))
                .contents("강연내용3")
                .build());

        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now().plusDays(2);

        from = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0,0,0);
        to = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth(), 0,0,0);

        List<Lecture> entityList = lectureRepository.findByStartDateBetween(from,to);

        assertThat(entityList.size()).isEqualTo(2);
        assertThat(entityList.get(0).getLectureId()).isEqualTo(lecture.getLectureId());
        assertThat(entityList.get(1).getLectureId()).isEqualTo(lec.getLectureId());
    }

    @Test
    @DisplayName("사번별 신청한 강연 목록 조회")
    public void findLectureListByEmployeeId() {
        String employeeId = "T0001";

        applicationRepository.save(Application.builder()
                .lectureId(lecture.getLectureId())
                .employeeId(employeeId)
                .applicationStatus(1)
                .createdAt(LocalDateTime.now())
                .build());

        List<Lecture> list = lectureRepository.findLectureListByEmployeeId(employeeId);

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getPresenter()).isEqualTo(lecture.getPresenter());
        assertThat(list.get(0).getLocation()).isEqualTo(lecture.getLocation());
        assertThat(list.get(0).getMaxAttendees()).isEqualTo(lecture.getMaxAttendees());
        assertThat(list.get(0).getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).isEqualTo(lecture.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(list.get(0).getContents()).isEqualTo(lecture.getContents());

        //case1: 신청한 강연을 수강취소 하면
        applicationRepository.save(Application.builder()
                .lectureId(lecture.getLectureId())
                .employeeId(employeeId)
                .applicationStatus(0)
                .createdAt(LocalDateTime.now())
                .build());

        list = lectureRepository.findLectureListByEmployeeId(employeeId);
        assertThat(list.size()).isEqualTo(0);

        //case2: 새로운 강연을 수강하면
        Lecture lecture2 = Lecture.builder()
                .presenter("강연자2")
                .location("강연장2")
                .maxAttendees(20)
                .startDate(LocalDateTime.now())
                .contents("강연내용2")
                .build();

        lectureRepository.save(lecture2);

        applicationRepository.save(Application.builder()
                .lectureId(lecture2.getLectureId())
                .employeeId(employeeId)
                .applicationStatus(1)
                .createdAt(LocalDateTime.now())
                .build());

        list = lectureRepository.findLectureListByEmployeeId(employeeId);

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getPresenter()).isEqualTo(lecture2.getPresenter());
        assertThat(list.get(0).getLocation()).isEqualTo(lecture2.getLocation());
        assertThat(list.get(0).getMaxAttendees()).isEqualTo(lecture2.getMaxAttendees());
        assertThat(list.get(0).getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).isEqualTo(lecture2.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(list.get(0).getContents()).isEqualTo(lecture2.getContents());
    }
}
