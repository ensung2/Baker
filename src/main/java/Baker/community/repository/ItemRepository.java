package Baker.community.repository;

import Baker.community.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 레시피명으로 조회(findBy~, 엔티티명 생략 가능)
    List<Item> findByItemName(String itemName);
}
