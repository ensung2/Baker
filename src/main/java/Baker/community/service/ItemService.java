package Baker.community.service;

import Baker.community.dto.ItemDto;
import Baker.community.entity.Item;
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

    public Long saveItem(ItemDto itemDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 1) 상품 등록
        Item item = new Item();
        itemRepository.save(item);

        // 2) 이미지 등록

        return  item.getId();
    }

}
