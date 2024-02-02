package Baker.community.repository;

import Baker.community.constant.ItemType;
import Baker.community.entity.Item;
import Baker.community.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("querydsl 조회 테스트")
    public void querydslTest() {
        //given
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        // when
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemType.eq(ItemType.BREAD))
                .where(qItem.itemName.like("%" + "레시피명" + "%"));
        //then
        List<Item> itemList = query.fetch();
        for (Item item:itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("레시피 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemType(ItemType.BREAD);
        item.setItemName("레시피명");
        item.setInfo("레시피 한줄설명");
        item.setMaterial("재료");
        item.setRecipe("레시피 순서");

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemType(ItemType.BREAD);
            item.setItemName("레시피명" + i);
            item.setInfo("레시피 한줄설명" + i);
            item.setMaterial("재료" + i);
            item.setRecipe("레시피 순서" + i);

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