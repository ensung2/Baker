package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListDto {

    private Long id;                    // 레시피 번호

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private String material;            // 레시피 재료

    private LocalDateTime regTime;      // 등록 시간
    private LocalDateTime updateTime;   // 수정 시간
}
