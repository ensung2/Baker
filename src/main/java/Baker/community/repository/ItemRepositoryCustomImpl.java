package Baker.community.repository;

import Baker.community.constant.ItemType;
import Baker.community.dto.ItemSearchDto;
import Baker.community.entity.Item;
import Baker.community.entity.QItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ItemRepositoryCustomImpl  implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchItemTypeEq(ItemType searchItemType) {
        return searchItemType == null ? null : QItem.item.itemType.eq(searchItemType);
    }

    private BooleanExpression searchByLike(String searchQuery){
        return QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(searchItemTypeEq(itemSearchDto.getSearchitemType()),
                        searchByLike(itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
