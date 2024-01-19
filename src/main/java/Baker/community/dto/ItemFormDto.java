package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;                    // 레시피 번호

    @NotNull(message = "레시피 타입을 선택 해 주세요.")
    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    @NotBlank(message = "레시피명을 입력 해 주세요.")
    private String itemName;            // 레시피명

    private String info;                // 레시피 한 줄 설명

    @NotBlank(message = "레시피 재료를 입력 해 주세요.")
    private String material;            // 레시피 재료

    @NotBlank(message = "레시피 설명을 입력 해 주세요.")
    private String recipe;              // 레시피 설명

    private List<ItemImgDto> itemImgDto = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

//    public Item createItem() {
//        return modelMapper.map(this, Item.class);
//    }


//    public Item createItem() {
//
//        Item item = new Item();
//        ItemFormDto itemFormDto = new ItemFormDto();
//
//        item.setItemType(itemFormDto.getItemType());
//        item.setItemName(itemFormDto.getItemName());
//        item.setInfo(itemFormDto.getInfo());
//        item.setMaterial(itemFormDto.getMaterial());
//        item.setRecipe(itemFormDto.getRecipe());
//
//        return item;
//    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
