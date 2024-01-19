package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ListDto {

    private Long id;                    // 레시피 번호

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private LocalDateTime regTime;

    public ListDto(Item item) {
        this.id = item.getId();
        this.itemType = item.getItemType();
        this.itemName = item.getItemName();
        this.regTime = item.getRegTime();

    }
}
