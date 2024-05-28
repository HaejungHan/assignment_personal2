package com.sparta.schedule.repository;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByModifiedAtDesc();

    List<Schedule> findAllByUser(User user);

}
