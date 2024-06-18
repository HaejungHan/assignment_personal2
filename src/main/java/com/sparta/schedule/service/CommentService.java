package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.exceptionhandler.CustomException;
import com.sparta.schedule.exceptionhandler.ErrorCode;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 사용자의 댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto, User user) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findScheduleById(scheduleId);

        // 등록한 댓글 저장
        Comment comment = commentRepository.save(new Comment(schedule, requestDto, user));
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    // 사용자의 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto requestDto, User user) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findCommentById(commentId);
//        System.out.println("Comment User ID: " + comment.getUser().getId());
//        System.out.println("Current User ID: " + user.getId());

        // 댓글 작성자와 수정하려는 사용자가 같은지 작성자가 null인지 확인
        comment.isExistUser(user);
        // 일정 수정
        comment.update(requestDto);

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    // 사용자의 댓글 삭제
    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, User user) {
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);

        // 댓글 작성자와 수정하려는 사용자가 같은지 작성자가 null인지 확인
        comment.isExistUser(user);
        commentRepository.delete(comment);
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

    // 선택한 일정의 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);
        List<Comment> commentList = schedule.getCommentList();
        List<CommentResponseDto> commentAllList = new ArrayList<>();
        for (Comment commentResponseDto : commentList) {
            commentAllList.add(new CommentResponseDto(commentResponseDto));
        }
        return commentAllList;
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

}
