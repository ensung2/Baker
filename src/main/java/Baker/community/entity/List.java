package Baker.community.entity;

import Baker.community.constant.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "list")
@Getter
@Setter
@ToString
public class List  extends CreateModify{

    @Id
    @Column(name = "list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 레시피 번호

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType listType;          // 레시피 타입 (bread, cookie, cake)

    @Column(nullable = false, length = 15)
    private String listName;            // 레시피명

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;



}
