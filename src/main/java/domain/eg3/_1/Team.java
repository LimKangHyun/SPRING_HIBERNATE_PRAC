package domain.eg3._1;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "teams")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;

    @Column(length = 50)
    private String name;

// 양방향 관계 설정 -> Player엔티티에 있는 team 필드 참조
//    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
// 부모 엔티티를 저장(persist)할 때, 연관된 자식 엔티티도 함께 저장됨
//    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
// 부모의 모든 생명주기 이벤트(저장, 수정, 삭제 등)가 자식에게 전파됨
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    // 자바 내부에서만 관리 -> DB의 컬럼이 아님
    private List<Player> players;
}
