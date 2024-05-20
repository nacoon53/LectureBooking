package com.booking.lecture.lecturebookingproject.service;

import com.booking.lecture.lecturebookingproject.domain.ApplicationRepository;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {
    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    @DisplayName("전체 강연 조회하기")
    void findAllLectures() {
        //given
        List<Lecture> lectures = new ArrayList<>();

        lectures.add(Lecture.builder()
                .presenter("강연자1")
                .location("강연위치1")
                .maxAttendees(10)
                .startDate(LocalDateTime.now())
                .contents("강연내용1")
                .build());

        lectures.add(Lecture.builder()
                .presenter("강연자2")
                .location("강연위치2")
                .maxAttendees(20)
                .startDate(LocalDateTime.now())
                .contents("강연내용2")
                .build());

        when(lectureRepository.findAll()).thenReturn(lectures);

        //when
        List<LectureResponseDto> result = lectureService.findAllLectures();

        //then
        assertThat(result.size()).isEqualTo(2);
        verify(lectureRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("강연 등록하기")
    void createLecture() {
        //given
        LectureRequestDto req = LectureRequestDto.builder()
                .presenter("강연자1")
                .location("강연위치1")
                .maxAttendees(10)
                .startDate(LocalDateTime.now())
                .contents("강연내용1")
                .build();

        when(lectureRepository.save(any(Lecture.class))).thenReturn(req.toEntity());

        //when
        LectureResponseDto res = lectureService.createLecture(req);

        //then
        assertThat(res).isNotNull();
        verify(lectureRepository).save(any());
    }

    @Test
    @DisplayName("오늘을 기준으로 강연 시작 시간 1주일 전부터 1일 후까지인 강연 목록 조회")
    void findAvailableLectures() {
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now().plusDays(2);

        from = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0,0,0);
        to = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth(), 0,0,0);

        //given
        List<Lecture> lectures = new ArrayList<>();

        lectures.add(Lecture.builder()
                .presenter("강연자1")
                .location("강연위치1")
                .maxAttendees(10)
                .startDate(LocalDateTime.now().minusDays(5))
                .contents("강연내용1")
                .build());

        when(lectureRepository.findByStartDateBetween(from, to)).thenReturn(lectures);

        //when
        List<LectureResponseDto> list = lectureService.findAvailableLectures();

        //then
        assertThat(list.size()).isEqualTo(1);
        verify(lectureRepository, times(1)).findByStartDateBetween(from, to);
    }

    @Test
    @DisplayName("최대 참여 인원이 1명인 강연에 인원 초과했는지 확인")
    void canApply() {
        //given
        //최대 참여 인원이 1명인 강연 등록
        Lecture lec = Lecture.builder()
                .lectureId(9999L)
                .presenter("강연자1")
                .location("강연위치1")
                .maxAttendees(1)
                .startDate(LocalDateTime.now().minusDays(10))
                .contents("강연내용1")
                .build();

        List<String> list = new ArrayList<>();

        when(lectureRepository.findById(lec.getLectureId())).thenReturn(Optional.of(lec));
        when(applicationRepository.findApplicationsByLectureId(lec.getLectureId())).thenReturn(list);

        assertThat(lectureService.canApply(lec.getLectureId())).isTrue();
        verify(lectureRepository, times(1)).findById(lec.getLectureId());

        //참여 신청 1
        list.add("T0001");

        when(applicationRepository.findApplicationsByLectureId(lec.getLectureId())).thenReturn(list);

        assertThat(lectureService.canApply(lec.getLectureId())).isFalse();
        verify(lectureRepository, times(2)).findById(lec.getLectureId());

    }

    @Test
    @DisplayName("사번별 신청한 강연 목록 조회")
    void findApplicationsByEmployeeId() {
        String employeeId = "T0001";
        //given
        Lecture lec = Lecture.builder()
                .lectureId(9999L)
                .presenter("강연자1")
                .location("강연위치1")
                .maxAttendees(1)
                .startDate(LocalDateTime.now().minusDays(10))
                .contents("강연내용1")
                .build();

        List<Lecture> givenList = new ArrayList<>();
        givenList.add(lec);

        when(lectureRepository.findLectureListByEmployeeId(employeeId)).thenReturn(givenList);

        //when
        List<LectureResponseDto> res = lectureService.findApplicationsByEmployeeId(employeeId);

        assertThat(res).isNotNull();
        assertThat(res.get(0).getLectureId()).isEqualTo(lec.getLectureId());
        assertThat(res.get(0).getPresenter()).isEqualTo(lec.getPresenter());
        assertThat(res.get(0).getLocation()).isEqualTo(lec.getLocation());
        assertThat(res.get(0).getMaxAttendees()).isEqualTo(lec.getMaxAttendees());
        assertThat(res.get(0).getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).isEqualTo(lec.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertThat(res.get(0).getContents()).isEqualTo(lec.getContents());
        verify(lectureRepository, times(1)).findLectureListByEmployeeId(employeeId);

    }
}
