package Baker.community.service;

import Baker.community.dto.AddItemDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item save(AddItemDto addItemDto)  {
        return itemRepository.save(addItemDto.toEntity());

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

}
