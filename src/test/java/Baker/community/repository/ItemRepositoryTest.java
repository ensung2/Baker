package Baker.community.repository;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("레시피 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemType(ItemType.Bread);
        item.setName("소금빵");
        item.setInfo("겉바속촉에 짭짤함까지! 매력적인 빵");
        item.setMaterial("* 기본반죽(단과자빵 반죽) : 강력분 300g, 이스트 4g, 설탕 30g, 소금 3g, 버터 30g, 우유 210g");
        item.setRecipe("1. 이렇게, 2. 저렇게, 3. 맛있게, 4. 구우면, 5. 완성!");
        item.setImg("123");
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

}