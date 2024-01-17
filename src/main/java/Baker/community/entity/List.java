package Baker.community.entity;

import Baker.community.constant.ItemType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "list")
@Data
public class List  extends CreateModify{

    @Id
    @Column(name = "list_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                    // 레시피 번호

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType listType;          // 레시피 타입 (bread, cookie, cake)

    @Column(nullable = false, length = 15)
    private String listName;            // 레시피명

    @Length(min = 1, max = 25)
    private String info;                // 레시피 한 줄 설명

    @Column(nullable = false, length = 200)
    private String material;            // 레시피 재료

    @Lob
    @Column(nullable = false)
    private String recipe;              // 레시피 설명

/*    private LocalDateTime regTime;      // 등록 시간
    private LocalDateTime updateTime;   // 수정 시간*/
}
