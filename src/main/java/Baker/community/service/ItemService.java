package Baker.community.service;

import Baker.community.dto.ItemFormDto;
import Baker.community.dto.ItemSearchDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.entity.ItemImg;
import Baker.community.repository.ItemImgRepository;
import Baker.community.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//    @Transactional
//    public Item save(AddItemDto addItemDto, List<MultipartFile> multipartFileList)  {
//        return itemRepository.save(addItemDto.toEntity());
//    }

    // 상품 등록 구현
    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception{

        // 상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

//    // Item에 있는 모든 데이터 조회
//    public List<Item> findAll() {
//        return itemRepository.findAll();
//    }

    // 레시피 글 하나 조회 (없으면 IllegalArgumentException 예외 발생)
    public Item findById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found" +id));
    }

//    // 레시피 목록 조회
//    public Page<Item> getList(int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("regTime"));
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));       // 한 페이지에 보여줄 레시피 갯수
//        return this.itemRepository.findAll(pageable);
//    }

    // 레시피 삭제
    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public Item updateItem(long id, UpdateItemDto updateDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found" + id));

        item.updateItem(
                updateDto.getItemType(),
                updateDto.getItemName(),
                updateDto.getInfo(),
                updateDto.getMaterial(),
                updateDto.getRecipe());

        return item;
    }

    // 상품 데이터 조회
    @Transactional(readOnly = true)
    public Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getItemPage(itemSearchDto, pageable);
    }

}
