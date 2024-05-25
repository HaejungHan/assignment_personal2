package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleId(Long schedule_id);
}