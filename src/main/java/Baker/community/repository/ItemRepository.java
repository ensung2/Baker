package Baker.community.repository;

import Baker.community.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>,
ItemRepositoryCustom{

    // 레시피명으로 조회(findBy~, 엔티티명 생략 가능)
    List<Item> findByItemName(String itemNm);

    Page<Item> findAll(Pageable pageable);

    List<Item> findByMemberId(Long memberId);
}
