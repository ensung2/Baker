package Baker.community.service;

import Baker.community.dto.ItemFormDto;
import Baker.community.dto.ItemImgDto;
import Baker.community.dto.ItemSearchDto;
import Baker.community.entity.Item;
import Baker.community.entity.ItemImg;
import Baker.community.repository.ItemImgRepository;
import Baker.community.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    // 레시피 등록 구현
    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception{

        // 레시피 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 레시피 이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    // 등록된 레시피 불러오기
    @Transactional(readOnly = true)                 // 읽기전용 트랜잭션
    public ItemFormDto getItemDtl(Long itemId){

        // 레시피 이미지 조회 (오름차순)
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        // 레시피 아이디 조회 후 레시피 엔티티 조회 (존재하지 않을시 오류 발생)
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);  // (24.02.03) 해당 코드 누락으로 이미지 저장 오류, 해결했음!
        return itemFormDto;
    }

    // Item에 있는 모든 데이터 조회
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    // 레시피 글 하나 조회 (없으면 IllegalArgumentException 예외 발생)
    public Item findById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found" +id));
    }

    // 레시피 업데이트(레시피, 레시피 이미지)
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList)throws Exception {

        // 레시피 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();    // 이미지 아이디 리스트 조회

        // 이미지 수정 등록
        for (int i=0; i<itemImgFileList.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    // 레시피 삭제
    @Transactional
    public void deleteItem(Long itemId) throws Exception {
        // 레시피 아이디 조회 후 레시피 엔티티 조회 (존재하지 않을시 오류 발생)
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        // 레시피에 속한 이미지들 삭제
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        for (ItemImg itemImg : itemImgList) {
            itemImgService.deleteItemImg(itemImg.getId());
        }

        // 레시피 삭제
        itemRepository.delete(item);
    }

    // 상품 데이터 조회
    @Transactional(readOnly = true)
    public Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getItemPage(itemSearchDto, pageable);
    }

}
