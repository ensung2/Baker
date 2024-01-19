package Baker.community.service;

import Baker.community.dto.AddItemDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.repository.ItemImgRepository;
import Baker.community.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

//    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

//        // 1) 상품 등록
//        Item item = itemFormDto.createItem();
//        itemRepository.save(item);
//
//        // 2) 이미지 등록
//        for (int i=0;i<itemImgFileList.size();i++){
//            ItemImg itemImg = new ItemImg();
//            itemImg.setItem(item);
//
//            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
//        }
//
//        return item.getId();
//    }

    public Item savedItem(AddItemDto addItemDto) {
        return itemRepository.save(addItemDto.toEntity());
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found" +id));
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public Item updateItem(long id, UpdateItemDto updateItem) {
        Item item = itemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found" + id));

        item.updateItem(updateItem.getItemType(), updateItem.getItemName(), updateItem.getInfo(),
                updateItem.getMaterial(), updateItem.getRecipe());

        return item;
    }

}
