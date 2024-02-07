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
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ItemRepositoryCustomImpl  implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchItemTypeEq(ItemType searchItemType) {
        return searchItemType == null ? null : QItem.item.itemType.eq(searchItemType);
    }

/*    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if (StringUtils.equals("itemName", searchBy)) {
            return QItem.item.itemName.like("%" + searchQuery + "%");
        }else if (StringUtils.equals("createBy", searchBy)) {
            return QItem.item.createBy.like("%" + searchQuery + "%");
        }
        return Expressions.asBoolean(true).isTrue();
    }*/

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        BooleanExpression itemNameExpression = QItem.item.itemName.like("%" + searchQuery + "%");
        BooleanExpression createByExpression = QItem.item.createBy.like("%" + searchQuery + "%");

        if (StringUtils.equals("itemName", searchBy)) {
            return itemNameExpression;
        } else if (StringUtils.equals("createBy", searchBy)) {
            return createByExpression;
        }

        // 검색 조건이 주어지지 않았을 때 createBy 또는 itemName 중 어떤 것이든 검색 가능
        return itemNameExpression.or(createByExpression);
    }


    @Override
    public Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(searchItemTypeEq(itemSearchDto.getSearchItemType()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
