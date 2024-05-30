package com.sparta.schedule.service;

import com.sparta.schedule.dto.LoginRequestDto;
import com.sparta.schedule.dto.SignupRequestDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import com.sparta.schedule.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    // 회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        // 사용자 role
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, requestDto.getNickname(), role);
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }

        //JWT 생성 및 쿠키에 저장 후 response객체에 추가
        String accessToken = jwtUtil.createToken(user.getUsername(), user.getRole());
        String refreshToken = jwtUtil.createRefreshToken();

        jwtUtil.addJwtToCookie(accessToken, response);
    }

}
