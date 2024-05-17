package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    // 전체조회
    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회 (내림차순)
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    // 선택한 일정조회
    public List<ScheduleResponseDto> getSchedule(Long id) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        // DB 조회 선택한 일정 조회
        return scheduleRepository.findById(id).stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id,ScheduleRequestDto requestDto,Long password) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        // 일정 수정
        schedule.update(requestDto);
        scheduleRepository.save(schedule);
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    public Long deleteSchedule(Long id) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        // 일정 삭제
        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }


}
