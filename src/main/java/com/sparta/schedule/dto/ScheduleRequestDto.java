package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotBlank(message = "일정의 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "일정의 내용을 입력해주세요.")
    private String contents;
    @NotBlank(message = "담당자의 이름을 입력해주세요.")
    private String manager;
    @NotBlank(message = "사용자의 비밀번호를 입력해주세요.")
    private String password;
}
