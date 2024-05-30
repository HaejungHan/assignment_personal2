package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
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
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto, User user) {
        // 선택한 일정의 ID 입력 확인
        if(requestDto.getScheduleId() == null) {
            throw new IllegalArgumentException("선택한 일정 ID를 입력해주세요.");
        }
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findScheduleById(scheduleId);

        // 댓글 내용이 비어있는지 확인 - Validation 예외처리 필요
        if (requestDto.getContents().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }
        // 등록한 댓글 저장
        Comment comment = commentRepository.save(new Comment(schedule, requestDto, user));
        CommentResponseDto responseDto = new CommentResponseDto(schedule, comment);
        return responseDto;
    }

    // 댓글 전체조회
    public List<CommentResponseDto> getAllComment() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDto> responseDtoList = new ArrayList<>();
        for (Comment responseDto : commentList) {
            responseDtoList.add(new CommentResponseDto(responseDto));
        }
        return responseDtoList;
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto requestDto, User user) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findCommentById(commentId);
        // 댓글 작성자와 수정하려는 사용자가 같은지 확인
        if(!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        // 일정 수정
        comment.update(requestDto);

        CommentResponseDto responseDto = new CommentResponseDto(schedule, comment);
        return responseDto;
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, Long scheduleId,User user) {
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);

        // 댓글의 작성자와 삭제하려는 사용자와 일치하는지 확인
        if(!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
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
