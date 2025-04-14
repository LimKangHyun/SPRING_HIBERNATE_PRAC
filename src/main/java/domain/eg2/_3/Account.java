package domain.eg2._3;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(
        name = "ACCOUNT_SEQ_GENERATION",
        sequenceName = "ACCOUNT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
}