package eg1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class EntityManagerTest {

    // EntityManagerFactory는 내부적으로 DB를 관리하고 무겁기 때문에 한 번만 생성해서 공유를 하는게 일반적
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
    @DisplayName("DB 접속정보 확인")
    void connection_test() throws Exception {

        Map<String, Object> properties = entityManagerFactory.getProperties();
        String url = properties.get("jakarta.persistence.jdbc.url").toString();
        String user = properties.get("jakarta.persistence.jdbc.user").toString();
        String driver = properties.get("jakarta.persistence.jdbc.driver").toString();

        log.info("url: {}", url);
        log.info("user: {}", user);

        assertThat(url).isEqualTo("jdbc:mysql://localhost:3306/hibernate_prac");
        assertThat(driver).isEqualTo("com.mysql.cj.jdbc.Driver");
    }
}
