package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ViewItemDto {

    private Long id;

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    private String material;            // 레시피 재료

    private String recipe;              // 레시피 설명

    public ViewItemDto(Item item) {
        this.id = item.getId();
        this.itemType = item.getItemType();
        this.itemName = item.getItemName();
        this.info = item.getInfo();
        this.material = item.getMaterial();
        this.recipe = item.getRecipe();
    }
}
