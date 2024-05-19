package com.booking.lecture.lecturebookingproject.web.admin;

import com.booking.lecture.lecturebookingproject.service.ApplicationService;
import com.booking.lecture.lecturebookingproject.service.LectureService;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerTest {

    @Mock
    private LectureService lectureService;

    @Mock
    private ApplicationService applicationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllLectures() throws Exception {
        //강연 전체 목록 조회(GET - /admin/lectures)
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/lectures"))
                .andExpect(status().isOk())
                .andDo(print());

        //verify(lectureService).findAllLectures();
    }

    @Test
    public void createLecture() throws Exception {
        //강연 등록(POST - /admin/lectures)
        String presenter = "나경_테스트";
        String location = "서울시 영등포구";
        int maxAttendees = 30;
        LocalDateTime startTime = LocalDateTime.of(2024,06,03, 10,30, 0);
        String startTimeStr = startTime.format(DateTimeFormatter.ISO_DATE_TIME);
        String contents = "2024 트렌드";

        LectureRequestDto req = LectureRequestDto.builder()
                .presenter(presenter)
                .location(location)
                .maxAttendees(maxAttendees)
                .startTime(startTime)
                .contents(contents)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/lectures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lectureId").exists())
                .andExpect(jsonPath("$.presenter").value(presenter))
                .andExpect(jsonPath("$.location").value(location))
                .andExpect(jsonPath("$.maxAttendees").value(maxAttendees))
                .andExpect(jsonPath("$.startTime").value(startTimeStr))
                .andExpect(jsonPath("$.contents").value(contents))
                .andDo(print());

        //verify(lectureService).createLecture(req);
    }

    @Test
    public void findApplicationsByLectureId() throws Exception {
        //강연 신청자 목록 조회(GET - /admin/lectures/${강연 ID}/applications)
        Long lectureId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/lectures/"+lectureId+"/applications"))
                .andExpect(status().isOk())
                .andDo(print());

        //verify(applicationService).findApplicationsByLectureId(lectureId);
    }
}
