package eg1;

import domain.eg1.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import util.TestUtil;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static util.TestUtil.*;

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

    @Test
    @DisplayName("save test")
    void save_test() throws Exception {

        executeCommit(entityManager, () -> {
            Member member = genMember(genMemberName());
            entityManager.persist(member);

            return null;
        });

    }
    private Member genMember(String memberName) {
        return Member.builder()
                .id(memberName)
                .name(memberName)
                .build();
    }

    private static String genMemberName() {
        return "member" + genNumStr();
    }

    @Test
    @DisplayName("select test")
    void select_test() throws Exception {

        Member member = genMember(genMemberName());

        executeCommit(entityManager, () -> {
            entityManager.persist(member);
            // 위에서 생성한 member의 Id와 동일한 행 가져오기
            Member findMember = entityManager.find(Member.class, member.getId());

            assertThat(findMember).isEqualTo(member);

            log.info("Member: {}", member);
            log.info("findMember: {}", findMember);

            return null;
        });

        Member entityMember = executeCommit(entityManager, () -> {
            Member findMember = entityManager.find(Member.class, member.getId());
            assertThat(findMember.getId()).isEqualTo(member.getId());

            findMember.setName("ADMIN");
            return findMember;
        });

        executeCommit(entityManager, () -> {

            entityManager.detach(entityMember);
            entityMember.setName("MEMBER");
            return null;
        });

        executeCommit(entityManager, () -> {
            // 준영속상태에 있던 엔티티를 다시 영속 상태로 만든것
            Member mergedMember = entityManager.merge(member);

            assertThat(mergedMember.getId()).isEqualTo(member.getId());
            assertThat(mergedMember.getName()).isEqualTo("MEMBER");

            return null;
        });
    }
}
