package Baker.community.dto;

import Baker.community.constant.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private ItemType itemType;

    private String searchBy;

    private String searchQuery = "";
}
