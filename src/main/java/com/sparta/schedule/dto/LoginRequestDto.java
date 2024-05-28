package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class   LoginRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "username은 최소 4자이상 10자이하, 영어 소문자와 숫자로 구성되어야 합니다.")
    private String username;
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]{8,15}$", message = "password는 최소 8자 이상 15자 이하이며, 영어 대소문자와 숫자로 구성되어야 합니다.")
    private String password;
}
