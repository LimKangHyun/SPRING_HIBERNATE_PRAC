package eg3;

import domain.eg3._1.Player;
import domain.eg3._1.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.*;
import util.TestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
    // LAZY로딩된 팀
    @Test
    @DisplayName("proxy obj")
    void proxy_obj_test() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = null;

        try {
//            player 안에는 team 객체가 아직 안 들어있고, 대신 프록시가 들어 있음
            player = entityManager.find(Player.class, 1);
            log.info("player.getName(): {}", player.getName());

//            entityManager.detach(player);  // player 객체를 영속성 컨텍스트에서 분리

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        entityManager.close();

        // Lazy 로딩된 team 객체를 호출할 때 예외가 발생해야 함
        Team team = player.getTeam();
        log.info("team = {}", team);

        // team 객체가 프록시 객체인지 확인
        log.info("team is a proxy? {}", team instanceof org.hibernate.proxy.HibernateProxy);

        // team 객체의 Lazy 로딩을 트랜잭션 밖에서 시도하면 LazyInitializationException이 발생해야 한다.
        assertThatThrownBy(() -> {
            team.getName();  // 이 시점에서 Lazy 로딩이 실패하고 예외 발생
        }).isInstanceOf(LazyInitializationException.class);  // 예외가 발생해야 한다.
    }

    @Test
    @DisplayName("get reference exception test")
    void get_reference_exception_test() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = null;

        try {
            player = entityManager.getReference(Player.class, 1);
        } catch (Exception e) {
            transaction.rollback();
        }
        transaction.commit();
//        entityManager.close();
        log.info("player.getName() = {}", player.getName());
    }

    @Test
    @DisplayName("proxy check")
    void proxy_check_test() throws Exception {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = null;

        try {
//            @OneToMany(fetch = EAGER) 설정과는 관계 없이
//            무조건 프록시 객체를 리턴
            player = entityManager.getReference(Player.class, 1);
        } catch ( Exception e ) {
            transaction.rollback();
        }

        transaction.commit();
        entityManager.close();

        boolean result = entityManagerFactory.getPersistenceUnitUtil().isLoaded(player);
        log.info("result = {}", result);
    }
}
