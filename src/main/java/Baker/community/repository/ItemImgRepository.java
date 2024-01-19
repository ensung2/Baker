package Baker.community.repository;

import Baker.community.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    // 이미지 등록 테스트 메소드
    List<ItemImg> findByItemIdOrderByIdAsc(Long ItemId);

}
