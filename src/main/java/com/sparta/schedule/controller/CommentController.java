package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public CommentResponseDto createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createComment(scheduleId, commentRequestDto);
    }

    @PutMapping("/{scheduleId}/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentId, scheduleId, commentRequestDto);
    }

    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @PathVariable Long scheduleId) {
        commentService.deleteComment(commentId, scheduleId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
