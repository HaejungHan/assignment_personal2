package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 사용자의 일정 등록
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, User user) {
        Schedule schedule = scheduleRepository.save(new Schedule(requestDto, user));
        return new ScheduleResponseDto(schedule);
    }

    // 사용자의 일정 전체 조회
    public List<ScheduleResponseDto> getSchedules(User user) {
        List<Schedule> schedules = scheduleRepository.findAllByUser(user);
        List<ScheduleResponseDto> responseDtoList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            responseDtoList.add(new ScheduleResponseDto(schedule));
        }
        return responseDtoList;
    }

    // 사용자의 선택한 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, User user) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        // 작성자와 현 사용자가 일치한지 확인
        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        // 사용자의 비밀번호와 일치하는지 확인
//        if(checkPWAndFindSchedule(schedule, requestDto)) {
        schedule.update(requestDto);
//        } else {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    // 사용자의 선택한 일정 삭제
    @Transactional
    public void deleteSchedule(Long id, User user) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findSchedule(id);
        // 작성자와 현 사용자가 일치한지 확인
        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        // 일정 등록할 때 설정한 비밀번호와 일치하는지 확인
//        if(checkPWAndFindSchedule(schedule, requestDto)) {
        scheduleRepository.delete(schedule);
//        }
    }

    // 선택한 일정과 댓글 조회
    public ScheduleResponseDto getScheduleWithComments(Long id) {
        Schedule schedule = findSchedule(id);
        List<CommentResponseDto> comments = commentService.getComments(id);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule, comments);
        return responseDto;
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponseDto> responseDtoList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            responseDtoList.add(new ScheduleResponseDto(schedule));
        }
        return responseDtoList;
    }

//    private boolean checkPWAndFindSchedule (Schedule schedule, ScheduleRequestDto requestDto) {
//        if(schedule.getPassword().equals(requestDto.getPassword())){
//            return true;
//        } else {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }


}
