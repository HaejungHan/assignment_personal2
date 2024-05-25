package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long scheduleId;
    private Long id;
    private String contents;
    private LocalDateTime createdAt;

    public CommentResponseDto(Schedule schedule, Comment comment) {
        this.scheduleId = schedule.getId();
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }
}
