package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private ItemType searchItemType;          // 조회할 아이템 타입

    private String searchBy;      // 조회할 작성자명 & 레시피명

    private String searchQuery = "";    // 조회할 검색어 저장 변수
}
