package com.booking.lecture.lecturebookingproject.web.admin;

import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.domain.LectureRepository;
import com.booking.lecture.lecturebookingproject.service.ApplicationService;
import com.booking.lecture.lecturebookingproject.service.LectureService;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class LectureControllerTest {

    @MockBean
    private LectureService lectureService;

    @MockBean
    private ApplicationService applicationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private Lecture lecture;

    @BeforeEach
    public void setUp() throws Exception{
        lecture = Lecture.builder()
                .presenter("강연자1")
                .location("강연장1")
                .maxAttendees(10)
                .startDate(LocalDateTime.now())
                .contents("강연내용1")
                .build();
    }
    @Test
    @DisplayName("강연 전체 목록 조회")
    public void findAllLectures() throws Exception {
        //강연 전체 목록 조회(GET - /admin/lectures)

        List<LectureResponseDto> list = new ArrayList<>();
        list.add(new LectureResponseDto(lecture));

        when(lectureService.findAllLectures()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/lectures"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(lectureService).findAllLectures();
    }

    @Test
    @DisplayName("강연 등록")
    public void createLecture() throws Exception {
        //강연 등록(POST - /admin/lectures)
        String presenter = "나경_테스트";
        String location = "서울시 영등포구";
        int maxAttendees = 30;
        LocalDateTime startDate = LocalDateTime.of(2024,06,03, 10,30, 0);
        String startDateStr = startDate.format(DateTimeFormatter.ISO_DATE_TIME);
        String contents = "2024 트렌드";

        LectureRequestDto req = LectureRequestDto.builder()
                .presenter(presenter)
                .location(location)
                .maxAttendees(maxAttendees)
                .startDate(startDate)
                .contents(contents)
                .build();

        when(lectureService.createLecture(req)).thenReturn(new LectureResponseDto(req.toEntity()));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lectures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.lectureId").exists())
//                .andExpect(jsonPath("$.presenter").value(presenter))
//                .andExpect(jsonPath("$.location").value(location))
//                .andExpect(jsonPath("$.maxAttendees").value(maxAttendees))
//                .andExpect(jsonPath("$.startDate").value(startDateStr))
//                .andExpect(jsonPath("$.contents").value(contents))
                .andDo(print());

        //verify(lectureService).createLecture(req);
    }

    @Test
    public void findApplicationsByLectureId() throws Exception {
        //강연 신청자 목록 조회(GET - /admin/lectures/${강연 ID}/applications)
        long lectureId = 9999L;

        List<String> list = new ArrayList<>();
        list.add("T0001");

        when(applicationService.findApplicationsByLectureId(lectureId)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/lectures/"+lectureId+"/applications"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(applicationService).findApplicationsByLectureId(lectureId);
    }
}
