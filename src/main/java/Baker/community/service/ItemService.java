package Baker.community.service;

import Baker.community.dto.ItemDto;
import Baker.community.entity.Item;
import Baker.community.entity.ItemImg;
import Baker.community.repository.ItemImgRepository;
import Baker.community.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemDto itemDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 1) 상품 등록
        Item item = itemDto.createItem();
        itemRepository.save(item);

        // 2) 이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

}
