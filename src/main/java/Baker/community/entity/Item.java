package Baker.community.entity;


import Baker.community.constant.ItemType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends CreateModify{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", updatable = false)
    private Long id;                    // 레시피 번호

    @Enumerated(EnumType.STRING)        // enum 타입 매핑 에노테이션
    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    @Column(name = "item_name", length = 15, nullable = false)
    private String itemName;            // 레시피명

    @Column(name = "item_info", length = 25)
    private String info;                // 레시피 한 줄 설명

    @Column(nullable = false, length = 200)
    private String material;            // 레시피 재료

    @Lob
    @Column(nullable = false)
    private String recipe;              // 레시피 설명

    // 빌더 패턴 방식으로 객체 생성 (필드에 어떤 값이 들어가는지 명시적으로 파악 가능)
    @Builder
    public Item(ItemType itemType, String itemName, String info, String material, String recipe) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.info = info;
        this.material = material;
        this.recipe = recipe;
    }

    // 엔티티에 요청받은 내용으로 값을 수정
    public void updateItem(ItemType itemType, String itemName, String info, String material, String recipe) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.info = info;
        this.material = material;
        this.recipe = recipe;
    }


    //    public static Item createItem(ItemFormDto itemFormDto) {
//        Item item = new Item();
//
//        item.setItemType(itemFormDto.getItemType());
//        item.setItemName(itemFormDto.getItemName());
//        item.setInfo(itemFormDto.getInfo());
//        item.setMaterial(itemFormDto.getMaterial());
//        item.setRecipe(itemFormDto.getRecipe());
//
//        return item;
//    }

//    public void updateItem(ItemFormDto itemFormDto) {
//        this.itemName = itemFormDto.getItemName();
//        this.itemType = itemFormDto.getItemType();
//        this.info = itemFormDto.getInfo();
//        this.material = itemFormDto.getMaterial();
//        this.recipe = itemFormDto.getRecipe();
//    }
}
