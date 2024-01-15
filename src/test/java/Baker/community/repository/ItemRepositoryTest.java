package Baker.community.repository;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("레시피 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemType(ItemType.Bread);
        item.setItemName("레시피명");
        item.setInfo("레시피 한줄설명");
        item.setImg("이미지.jpg");
        item.setMaterial("재료");
        item.setRecipe("레시피 순서");
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemType(ItemType.Bread);
            item.setItemName("레시피명" + i);
            item.setInfo("레시피 한줄설명" + i);
            item.setImg("이미지" + i + ".jpg");
            item.setMaterial("재료" + i);
            item.setRecipe("레시피 순서" + i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("레시피명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("레시피명1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }


}