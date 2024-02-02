package Baker.community.repository;

import Baker.community.dto.ItemSearchDto;
import Baker.community.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    // 상품 조회 조건을 담고 있는 객체와 페이징 정보를 담고있는 객체를 파라미터로 받음
    Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
