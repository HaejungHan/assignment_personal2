package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 사용자의 댓글 등록
    @PostMapping("/user/schedule/{scheduleId}/comment")
    public ResponseEntity<String> createComment(@PathVariable Long scheduleId,
                                                @RequestBody @Valid CommentRequestDto commentRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(scheduleId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>("댓글이 성공적으로 등록되었습니다.", HttpStatus.OK);
    }

    // 사용자의 댓글 수정
    @PutMapping("/user/schedule/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(scheduleId, commentId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>("댓글이 성공적으로 수정되었습니다", HttpStatus.OK);
    }

    // 사용자의 댓글 삭제
    @DeleteMapping("/user/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long scheduleId,
                                                @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(scheduleId, commentId, userDetails.getUser());
        return new ResponseEntity<>("댓글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    // 댓글 전체 조회
    @GetMapping("/comments")
    public List<CommentResponseDto> getAllComment() {
        return commentService.getAllComment();
    }

    // 선택한 일정의 댓글 조회
    @GetMapping("/schedule/{scheduleId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long scheduleId) {
        return commentService.getComments(scheduleId);
    }

}
