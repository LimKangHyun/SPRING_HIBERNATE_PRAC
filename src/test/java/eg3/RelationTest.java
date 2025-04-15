package eg3;

import domain.eg3._1.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import util.TestUtil;

import java.util.ArrayList;
import java.util.List;

import static util.TestUtil.*;

@Slf4j
public class RelationTest {

    static EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @BeforeAll
    static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-mysql");
    }
    @AfterEach
    void close() {
        entityManager.close();
    }
    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }
    
    @Test
    @DisplayName("table strategy test")
    void table_stg_test() throws Exception {
        // ddl-auto가 create인 경우에는 해당 테스트 불가
        List<Player> bus = new ArrayList<>();

        executeCommit(entityManager, () -> {
            Player player = entityManager.find(Player.class, 1);
            bus.add(player);
        });

        Player findPlayer = bus.get(0);
        log.info("findPlayer.getName(): {}", findPlayer.getName());
    }
}
