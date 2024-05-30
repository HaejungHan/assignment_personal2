
package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 회원의 일정 등록
    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return scheduleService.createSchedule(requestDto, userDetails.getUser());
    }

    // 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return scheduleService.getSchedules(userDetails.getUser());
    }

    // 선택한 일정 조회
    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    // 선택한 일정의 댓글 조회
//    @GetMapping("/schdulde/{id}/comments")
//    public List<CommentResponseDto> getAllCommentInSchedule(@PathVariable Long scheduleId) {
//        return scheduleService.getAllCommentInSchedule(scheduleId);
//    }

    // 작성한 사용자의 선택한 일정 수정
    @PutMapping("/schedule/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id,
                                              @RequestBody @Valid ScheduleRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return scheduleService.updateSchedule(id,requestDto, userDetails.getUser());
    }

    // 작성한 사용자의 선택한 일정 삭제
    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable Long id,
                               @RequestBody @Valid ScheduleRequestDto requestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(id,requestDto, userDetails.getUser());
    }

    // 권한이 admin일 경우 전체 회원의 일정 조회
    @GetMapping("/admin/schedules")
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }


}