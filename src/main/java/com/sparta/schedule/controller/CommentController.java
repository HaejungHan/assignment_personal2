package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록

    @PostMapping("/{scheduleId}/comment")
    public CommentResponseDto createComment(@PathVariable Long scheduleId,
                                            @RequestBody @Valid CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(scheduleId, commentRequestDto, userDetails.getUser());
    }

    // 댓글 전체 조회
    @GetMapping("/comments")
    public List<CommentResponseDto> getAllComment() {
        return commentService.getAllComment();
    }


    // 댓글 수정
    @PutMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @PathVariable Long scheduleId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
            commentService.updateComment(commentId, scheduleId, commentRequestDto, userDetails.getUser());
            return new ResponseEntity<>("성공적으로 수정되었습니다",HttpStatus.OK);
    }
    // 댓글 삭제
    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @PathVariable Long scheduleId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, scheduleId, userDetails.getUser());
        return new ResponseEntity<>("성공적으로 삭제되었습니다.", HttpStatus.OK);
    }
}
