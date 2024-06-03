
package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CommentService commentService;


    // 사용자의 일정 등록
    @PostMapping("/schedule")
    public ResponseEntity<String> createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.createSchedule(requestDto, userDetails.getUser());
        return new ResponseEntity<>("일정이 성공적으로 등록되었습니다.", HttpStatus.OK);
    }

    // 사용자의 일정 조회
    @GetMapping("/user/schedules")
    public List<ScheduleResponseDto> getSchedules(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return scheduleService.getSchedules(userDetails.getUser());
    }

    // 사용자의 선택한 일정 수정
    @PutMapping("/user/schedule/{id}")
    public ResponseEntity<String> updateSchedule(@PathVariable Long id,
                                                 @RequestBody @Valid ScheduleRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.updateSchedule(id, requestDto, userDetails.getUser());
        return new ResponseEntity<>("일정이 성공적으로 수정되었습니다.", HttpStatus.OK);
    }

    // 사용자의 선택한 일정 삭제
    @DeleteMapping("/user/schedule/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(id, userDetails.getUser());
        return new ResponseEntity<>("일정이 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    // 전체 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getAllSchedule() {
        return scheduleService.getAllSchedule();
    }

    // 선택한 일정 조회
    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getScheduleWithComments(@PathVariable Long id) {
        return scheduleService.getScheduleWithComments(id);
    }


}