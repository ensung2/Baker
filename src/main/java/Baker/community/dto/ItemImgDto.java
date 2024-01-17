package Baker.community.dto;

import Baker.community.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {

    private Long id;

    private String imgName;             // 파일명

    private String oriImgName;          // 원본 파일명

    private String imgUrl;              // 이미지 조회 경로

    private static ModelMapper modelMapper = new ModelMapper();

    // ItemImg 객체를 파라미터로 받은 후 같을때 ItemImgDto로 값을 복사하여 반환
    public static  ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
