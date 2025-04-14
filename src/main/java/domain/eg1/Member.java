package domain.eg1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Entity
// member 테이블과 매핑
@Table(name = "member")
// 기본 생성자 protected
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private String id;

    // name은 외부에서 변경 가능하도록 세터 생성
    @Setter
    private String name;

    @Builder
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
