package Baker.community.repository;

import Baker.community.entity.Item;
import Baker.community.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>,
ItemRepositoryCustom{

    // 레시피명으로 조회(findBy~, 엔티티명 생략 가능)
    List<Item> findByItemName(String itemNm);

    @Modifying
    @Query("update Item i set i.view = i.view + 1 where i.id = :itemId")
    int updateView(Long itemId);

    List<Item> findById(Member memberId);

    Page<Item> findAll(Pageable pageable);

    List<Item> findByMemberId(Long memberId);
}
