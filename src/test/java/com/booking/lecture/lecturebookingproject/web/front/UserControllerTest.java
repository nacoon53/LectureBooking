package com.booking.lecture.lecturebookingproject.web.front;

import com.booking.lecture.lecturebookingproject.domain.Application;
import com.booking.lecture.lecturebookingproject.domain.Lecture;
import com.booking.lecture.lecturebookingproject.service.ApplicationService;
import com.booking.lecture.lecturebookingproject.service.LectureService;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.ApplicationResponseDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureRequestDto;
import com.booking.lecture.lecturebookingproject.web.dto.LectureResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

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
    public void findPopularLectures() throws Exception {
        //인기 강연 목록 조회(GET - /api/vi/lectures?popular=true)
        List<LectureResponseDto> list = new ArrayList<>();
        list.add(new LectureResponseDto(lecture));

        when(lectureService.findPopularLectures()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lectures?popular=true"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(lectureService).findPopularLectures();
    }

    @Test
    public void findLectures() throws Exception {
        // 강연 목록 조회(GET - /api/vi/lectures)
        List<LectureResponseDto> list = new ArrayList<>();
        list.add(new LectureResponseDto(lecture));

        when(lectureService.findAvailableLectures()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lectures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(lectureService).findAvailableLectures();
    }

    @Test
    public void applyForLecture() throws Exception {
        //강연 신청(POST - /api/vi/lectures/apply)
        Long lectureId = 1L;
        String employeeId = "T0001";
        int applicationStatus = 1;

        ApplicationRequestDto req = ApplicationRequestDto.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(applicationStatus)
                .build();

        when(applicationService.applyForLecture(req)).thenReturn(new ApplicationResponseDto(req.toEntity()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/lectures/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andDo(print());
//                .andExpect(jsonPath("$.lectureId").value(lectureId))
//                .andExpect(jsonPath("$.employeeId").value(employeeId))
//                .andExpect(jsonPath("$.applicationStatus").value(applicationStatus))

 //       verify(applicationService).applyForLecture(req);
    }

    @Test
    public void cancelApplication() throws Exception {
        // 강연 신청 취소 (GET - /api/vi/lectures/cancel)
        Long lectureId = 1L;
        String employeeId = "T0001";
        int applicationStatus = 0;

        ApplicationRequestDto req = ApplicationRequestDto.builder()
                .lectureId(lectureId)
                .employeeId(employeeId)
                .applicationStatus(applicationStatus)
                .build();

        when(applicationService.applyForLecture(req)).thenReturn(new ApplicationResponseDto(req.toEntity()));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/lectures/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.lectureId").value(lectureId))
//                .andExpect(jsonPath("$.employeeId").value(employeeId))
//                .andExpect(jsonPath("$.applicationStatus").value(applicationStatus))
                .andDo(print());

//        verify(applicationService).cancelApplication(req);
    }

    @Test
    public void getApplicationsByEmployeeId() throws Exception {
        // 신청 내역 조회 (GET - /api/vi//users/{employeeId}/applications)
        String employeeId = "T0001";

        List<LectureResponseDto> list = new ArrayList<>();
        list.add(new LectureResponseDto(lecture));

        when(lectureService.findApplicationsByEmployeeId(employeeId)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"+employeeId+"/applications"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(lectureService).findApplicationsByEmployeeId(employeeId);

    }
}
