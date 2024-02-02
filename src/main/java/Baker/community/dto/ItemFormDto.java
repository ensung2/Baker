package Baker.community.dto;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;                    // 레시피 번호

    private ItemType itemType;          // 레시피 타입 (bread, cookie, cake)

    @NotBlank(message = "레시피명을 입력해주세요.")
    private String itemName;            // 레시피명

    @NotBlank(message = "레시피 한 줄 설명을 입력해주세요.")
    private String info;                // 레시피 한 줄 설명

    @NotBlank(message = "재료를 입력해주세요.")
    private String material;            // 레시피 재료

    @NotBlank(message = "레시피 전체설명을 입력해주세요.")
    private String recipe;              // 레시피 설명

    // 상품 저장 후 수정시 상품 이미지 정보를 저장하는 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    // 상품 아이디를 저장하는 리스트 (수정시 사용)
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // 엔티티 객체와 dto 객체 간의 데이터를 복사
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static  ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}
