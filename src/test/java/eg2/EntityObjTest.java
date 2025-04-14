package eg2;

import domain.eg2._3.GymMemberShip;
import domain.eg2._3.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import util.TestUtil;

import static util.TestUtil.*;

public class EntityObjTest {

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

    }

    @Test
    @DisplayName("enum test")
    void enum_test() throws Exception {

        executeCommit(entityManager, () -> {

            GymMemberShip memberShip1 = new GymMemberShip();
            memberShip1.setMemberShipLevel(Level.GOLD);

            GymMemberShip memberShip2 = new GymMemberShip();
            memberShip2.setMemberShipLevel(Level.SILVER);

            GymMemberShip memberShip3 = new GymMemberShip();
            memberShip3.setMemberShipLevel(Level.BRONZE);

            entityManager.persist(memberShip1);
            entityManager.persist(memberShip2);
            entityManager.persist(memberShip3);
            return null;
        });
    }
}
