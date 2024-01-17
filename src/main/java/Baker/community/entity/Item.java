package Baker.community.entity;


import Baker.community.constant.ItemType;
import Baker.community.dto.ItemDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class Item  extends CreateModify{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                    // 레시피 번호

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // enum 타입 매핑 에노테이션
    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    @Column(name = "item_name", length = 15)
    private String itemName;                // 레시피명

    @Column(name = "item_info", length = 25)
    private String info;                // 레시피 한 줄 설명

    @Column(nullable = false, length = 200)
    private String material;            // 레시피 재료

    @Lob
    @Column(nullable = false)
    private String recipe;              // 레시피 설명

    public void updateItem(ItemDto itemDto) {
        this.itemName = itemDto.getItemName();
        this.itemType = itemDto.getItemType();
        this.info = itemDto.getInfo();
        this.material = itemDto.getMaterial();
        this.recipe = itemDto.getRecipe();
    }

}
