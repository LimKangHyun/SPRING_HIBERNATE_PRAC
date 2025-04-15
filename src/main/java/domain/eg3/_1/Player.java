package domain.eg3._1;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer id;

    private String name;

    @ManyToOne // 여러 플레이어가 하나의 팀에 속함 -> Player는 다수, Team은 하나
    @JoinColumn(name = "team_id") // 플레이어가 어떤 팀에 속하는지 연결 -> team_id를 외래키로 가져옴
    private Team team;
}
