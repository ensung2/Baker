package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemDto{   // test용으로 사용
    private Long id;

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    private String material;            // 레시피 재료

    private String recipe;              // 레시피 설명

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
