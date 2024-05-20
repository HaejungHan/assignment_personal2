
package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping("/schedules")
    public List<Schedule> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }


    @PutMapping("/schedule/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id,@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id,requestDto);
    }

    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable Long id,@RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id,requestDto);
    }

}