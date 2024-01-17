package Baker.community.service;

import Baker.community.constant.ItemType;
import Baker.community.dto.ItemDto;
import Baker.community.entity.Item;
import Baker.community.entity.ItemImg;
import Baker.community.repository.ItemImgRepository;
import Baker.community.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

            int i = 0;
            String path = "C:/work/project/recipeImg";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{0});
            multipartFileList.add(multipartFile);


        return multipartFileList;
    }


    @Test
    @DisplayName("상품 등록 테스트")
    void saveItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemType(ItemType.Bread);
        itemDto.setItemName("레시피명");
        itemDto.setInfo("레시피 간단설명");
        itemDto.setMaterial("레시피 재료");
        itemDto.setRecipe("레시피 설명");

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveItem(itemDto, multipartFileList);

        List<ItemImg> itemImgList = itemImgRepository.findByItemId(itemId);
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(itemDto.getItemType(), item.getItemType());
        assertEquals(itemDto.getItemName(), item.getItemName());
        assertEquals(itemDto.getInfo(), item.getInfo());
        assertEquals(itemDto.getMaterial(), item.getMaterial());
        assertEquals(itemDto.getRecipe(), item.getRecipe());
        assertEquals(multipartFileList.get(0).getOriginalFilename(),
                itemImgList.get(0).getOriImgName());
    }

}