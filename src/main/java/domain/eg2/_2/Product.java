package domain.eg2._2;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    // 새로운 Product 객체를 생성할 때마다 자동으로 증가된 값이 id 필드에 할당
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producted_name")
    private String name;
}
