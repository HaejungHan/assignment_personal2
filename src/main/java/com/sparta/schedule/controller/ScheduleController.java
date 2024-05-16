
package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
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

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedule() {
        return scheduleService.getSchedule();
    }


    @PutMapping("/schedule/{id}")
    public Long updateMemo(@PathVariable Long id,@PathVariable Long password,@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/schedule/{id}")
    public Long deleteMemo(@PathVariable Long id,@PathVariable Long password) {
        return scheduleService.deleteSchedule(id);
    }

}