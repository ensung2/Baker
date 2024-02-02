package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private ItemType searchitemType;          // 조회할 아이템 타입

    private String searchQuery = "";    // 조회할 검색어 저장 변수
}
