package domain.eg3._1;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;

    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "team") // 양방향 관계 설정 -> Player엔티티에 있는 team 필드 참조
    // 자바 내부에서만 관리 -> DB의 컬럼이 아님
    private List<Player> players;
}
