package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateItemDto {    // 레시피 글 수정 요청을 받을 DTO

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    private String material;            // 레시피 재료

    private String recipe;              // 레시피 설명

}
