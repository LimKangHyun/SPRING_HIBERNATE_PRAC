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

    public static <T> T executeCommit(EntityManager entityManager, TransactionalAction<T> action) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            // 트랜잭션 시작
            transaction.begin();
            // 비즈니스 로직 실행
            T result = action.execute();
            transaction.commit();
            return result;
        } catch (Exception e) {
            // 트랜잭션이 활성상태인경우인지 확인 후 롤백, 비활상태라면 롤백하면 예외 터지고 의미 없는 동작이므로 예외 처리
            if (transaction.isActive()) transaction.rollback();
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    @FunctionalInterface
    public interface TransactionalAction<T> {
        T execute() throws Exception;
    }
}
