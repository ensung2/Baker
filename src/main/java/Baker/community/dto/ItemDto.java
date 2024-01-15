package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;                    // 레시피 번호

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    private String img;                 // 레시피 사진

    private String material;            // 레시피 재료

    private String recipe;              // 레시피 설명

    private LocalDateTime regTime;      // 등록 시간
    private LocalDateTime updateTime;   // 수정 시간
}
