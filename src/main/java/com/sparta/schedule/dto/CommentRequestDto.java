package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long scheduleId;
    @NotBlank(message = "댓글의 내용을 입력해주세요.")
    private String contents;

}