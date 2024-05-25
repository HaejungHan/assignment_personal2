package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    public final CommentRepository commentRepository;

    // 댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {
        // 선택한 일정의 ID 입력 확인
        if(scheduleId == null) {
            throw new IllegalArgumentException("선택한 일정 ID를 입력해주세요. ");
        }
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findScheduleById(scheduleId);

        // 댓글 내용이 비어있는지 확인
        if (requestDto.getContents().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }
        // 등록한 댓글 저장
        Comment comment = commentRepository.save(new Comment(schedule, requestDto));
        CommentResponseDto responseDto = new CommentResponseDto(schedule, comment);
        return responseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findCommentById(commentId);
        // 일정 수정
        comment.update(requestDto);
        // 선택한 댓글의 사용자가 현재 사용자와 일치하는지 확인

        CommentResponseDto responseDto = new CommentResponseDto(schedule, comment);
        return responseDto;
    }

    public void deleteComment(Long commentId, Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);
        // 선택한 댓글의 사용자가 현재 사용자와 일치하는지 확인

        commentRepository.delete(comment);
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }

}
