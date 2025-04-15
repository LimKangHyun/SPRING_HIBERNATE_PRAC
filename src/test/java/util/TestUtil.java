package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {

    public static String genNumStr() {
        int number = (int) (Math.random() * 10000);
        return String.valueOf(number);
    }

    public static void executeCommit(EntityManager entityManager, Runnable action) {
        EntityTransaction transaction = entityManager.getTransaction();
        // 트랜잭션 시작
        transaction.begin();
        try {

            // 비즈니스 로직 실행
            action.run();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            transaction.commit();
        }
    }
}
