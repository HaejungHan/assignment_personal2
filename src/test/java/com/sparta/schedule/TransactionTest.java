////package com.sparta.schedule;
////
////public class TransactionTest {
////
////
////}
//
//package com.sparta.schedule;
//
//import com.sparta.schedule.entity.Schedule;
//import com.sparta.schedule.repository.ScheduleRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//public class TransactionTest {
//
//    @PersistenceContext
//    EntityManager em;
//
////    @Autowired
//    ScheduleRepository scheduleRepository;
//
//    @Test
//    @Transactional
//    @Rollback(value = false) // 테스트 코드에서 @Transactional 를 사용하면 테스트가 완료된 후 롤백하기 때문에 false 옵션 추가
//    @DisplayName("메모 생성 성공")
//    void test1() {
//        Schedule schedule = new Schedule();
//        schedule.setTitle("Robbert");
//        schedule.setContents("@Transactional 테스트 중!");
//
//        em.persist(schedule);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
//    }
//
//    @Test
//    @Disabled
//    @DisplayName("메모 생성 실패")
//    void test2() {
//        Schedule schedule = new Schedule();
//        schedule.setTitle("Robbert");
//        schedule.setContents("@Transactional 테스트 중!");
//
//        em.persist(schedule);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    @DisplayName("트랜잭션 전파 테스트")
//    void test3() {
//        scheduleRepository.createSchedule(em);
//        System.out.println("테스트 test3 메서드 종료");
//    }
//}
