package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemDto {

    private Long id;                    // 레시피 번호

    @NotNull
    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    @NotNull
    @Length(min = 1, max = 15)
    private String itemName;            // 레시피명

    @Length(min = 1, max = 25)
    private String info;                // 레시피 한 줄 설명

    @NotNull
    private String material;            // 레시피 재료

    @NotNull
    private String recipe;              // 레시피 설명

    private List<ItemImgDto> itemImgDto = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemDto of(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }
}
