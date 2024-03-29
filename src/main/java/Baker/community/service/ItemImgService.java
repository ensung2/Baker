package Baker.community.service;

import Baker.community.entity.ItemImg;
import Baker.community.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final ItemImgRepository itemImgRepository;
    private final S3Uploader s3Uploader;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";


        // 파일업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            // 상품 이미지 등록 시 해당 파라미터로 uploadFile메소드 호출 -> imgName 변수에 저장
            imgName = s3Uploader.uploadFile(itemImgFile);
            // 저장할 상품 이미지를 불러올 경로
            imgUrl = imgName;
        }

        // 상품 이미지 정보 저장
        /**
         *  originalName : 업로드했던 상품 이미지 파일의 원래 이름
         *  imgName : 실제 로컬에 저장되 상품 이미지 파일의 이름
         *  imgUrl : 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로
         */
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    // 레시피 수정 시 이미지도 수정할때 (변경 감지)
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if (!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);
        // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())){
                s3Uploader.deleteFile(savedItemImg.getImgName());
            }
            String originalName = itemImgFile.getOriginalFilename();
            String imgName = s3Uploader.uploadFile(itemImgFile);
            String imgUrl = imgName;
            savedItemImg.updateItemImg(originalName, imgName, imgUrl);
        }
    }

    // 레시피 삭제 시 이미지도 삭제
    public void deleteItemImg(Long itemImgId) throws Exception {
        ItemImg itemImg = itemImgRepository.findById(itemImgId)
                .orElseThrow(EntityNotFoundException::new);

        // 이미지 파일 삭제
        if (!StringUtils.isEmpty(itemImg.getImgName())) {
            s3Uploader.deleteFile(itemImg.getImgName());
        }

        // 이미지 엔티티 삭제
        itemImgRepository.delete(itemImg);
    }
}
