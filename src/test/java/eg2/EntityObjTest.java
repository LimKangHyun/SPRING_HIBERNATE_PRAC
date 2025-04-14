package eg2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

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
}
