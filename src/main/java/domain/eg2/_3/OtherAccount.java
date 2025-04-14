package domain.eg2._3;

import jakarta.persistence.*;

@Entity
@TableGenerator(
        name = "ACCOUNT_SEQ_TABLE", // TableGenerator의 이름
        table = "CST_SEQUENCE_CHECK", // 테이블 이름 (여기서 시퀀스를 관리)
        pkColumnName = "OTHER_ACCOUNT_SEQ", // 기본 키 열 이름 (시퀀스를 관리하는 열)
        initialValue = 1000, // 시작값을 1000으로 설정
        allocationSize = 1 // 시퀀스 증가 단위
)
// SequenceGenerator를 사용하여 ACCOUNT_SEQ라는 시퀀스를 관리
// allocationSize = 1로 설정되어 있어 ID는 1씩 증가
// 이 방식은 ACCOUNT_SEQ 시퀀스를 통해 고유한 ID 값을 생성
// Account -> 1, 2, 3
// OtherAccount -> 1001, 1002, 1003
// 따라서, OtherAccount 엔티티와 Account 엔티티는 각각 다른 방식으로 ID를 생성하며, ID 충돌이 발생하지 않도록 서로 다른 시퀀스(테이블 시퀀스 vs 시퀀스)를 사용
public class OtherAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ACCOUNT_SEQ_TABLE")
    private Long id;

    private String name;
}
