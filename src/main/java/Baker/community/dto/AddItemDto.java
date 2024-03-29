package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor      // 기본 생성자 추가
@AllArgsConstructor     // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class AddItemDto {       // 레시피 추가 코드 (테스트용으로 사용)

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    private String material;            // 레시피 재료

    private String recipe;              // 레시피 설명

    // 레시피 추가 시 저장할 엔티티로 변환
    public Item toEntity() {
        if (itemType == null || itemName == null || material == null || recipe == null) {
            throw new IllegalArgumentException("itemType, itemName, material, and recipe cannot be null");
        }
        return Item.builder()
                .itemType(itemType)
                .itemName(itemName)
                .info(info)
                .material(material)
                .recipe(recipe)
                .build();
    }


}
