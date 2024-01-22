package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ItemDto {

    @NotNull(message = "레시피 타입을 선택 해 주세요.")
    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    @NotBlank(message = "레시피명을 입력 해 주세요.")
    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    @NotBlank(message = "레시피 재료를 입력 해 주세요.")
    private String material;            // 레시피 재료

    @NotBlank(message = "레시피 설명을 입력 해 주세요.")
    private String recipe;              // 레시피 설명

    public ItemDto(Item item) {
        this.itemType = item.getItemType();
        this.itemName = item.getItemName();
        this.info = item.getInfo();
        this.material = item.getMaterial();
        this.recipe = item.getRecipe();
    }
}
