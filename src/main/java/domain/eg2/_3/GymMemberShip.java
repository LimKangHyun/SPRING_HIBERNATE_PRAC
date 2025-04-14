package domain.eg2._3;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class GymMemberShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
//    @Enumerated(EnumType.ORDINAL) -> enum의 순서값 사용해 저장(첫번째 값 0 , 두번째 값 1...)
    // ORDINAL은 저장공간이 적어지지만, enum의 순서가 바뀌면 뒤죽박죽되어 유지보수가 어렵다.ㅏ
    // STRING은 enum의 문자열그대로 저장하여, 순서가 바뀌어도 상관없다.
    @Enumerated(EnumType.STRING)
    private Level memberShipLevel;
}
