package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import lombok.Getter;

@Getter
public class NewItemDto {
    private final ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private final String itemName;            // 레시피명

    private final String info;                // 레시피 한 줄 설명

    private final String material;            // 레시피 재료

    private final String recipe;              // 레시피 설명

    public NewItemDto(Item item) {
        this.itemType = item.getItemType();
        this.itemName = item.getItemName();
        this.info = item.getInfo();
        this.material = item.getMaterial();
        this.recipe = item.getRecipe();
    }
}
