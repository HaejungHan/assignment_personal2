package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "schedule") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "manager", nullable = false)
    private String manager;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY) // 회원정보가 필요할 때만 가져오도록 lazy타입으로 설정
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public Schedule(ScheduleRequestDto requestDto, User user) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
        this.password = user.getPassword();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
    }

}