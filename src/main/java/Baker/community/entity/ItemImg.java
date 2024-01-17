package Baker.community.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_img")
@Data
public class ItemImg extends CreateModify{

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;             // 파일명

    private String oriImgName;          // 원본 파일명

    private String imgUrl;              // 이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 파라미터로 입력받아 이미지 정보 업데이트 메소드
    public void updateItemImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
