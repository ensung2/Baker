package Baker.community.controller;

import Baker.community.dto.ItemFormDto;
import Baker.community.service.ItemImgService;
import Baker.community.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private List<MultipartFile> multipartFileList;


/*    @GetMapping("/recipe/new/{id}")
    public ResponseEntity<ItemDto> findArticle(@PathVariable long id) {
        Item item = itemService.findById(id);

        return ResponseEntity.ok()
                .body(new ItemDto(item));
    }*/

/*    // 레시피 등록 템플릿 연결
    @GetMapping(value = "/recipe/new")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String recipeForm(Long id, Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        if (id == null) {
            model.addAttribute("item", new ViewItemDto());
        }else {
            Item item = itemService.findById(id);
            model.addAttribute("item", new ViewItemDto(item));
        }

        return "content/recipeForm";
    }*/

    // 레시피 등록 템플릿 연결
    @GetMapping(value = "/recipe/new")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String recipeForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "content/recipeForm";
    }

    // 레시피 등록
    @PostMapping(value = "/recipe/new")
    public String recipeNew(@Valid ItemFormDto itemFormDto,
                            BindingResult bindingResult, Model model,
                            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        // 등록값이 없으면 다시 등록폼으로 돌아감
        if (bindingResult.hasErrors()){
            return "content/recipeForm";
        }
        if ((itemImgFileList.get(0).isEmpty() && itemFormDto.getId()==null)){
            model.addAttribute("errorMessage", "상품 이미지를 넣어주세요.");
            return "content/recipeForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "content/recipeForm";
        }

        return "redirect:/";

    }

    // 레시피 아이디(데이터) 조회
    @GetMapping(value = "/recipe/new/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId,
                          Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "content/recipeForm";
        }
        return "content/recipeForm";
    }

/*    // 레시피 수정
    @PutMapping("/recipe/new/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id,
                                           @Valid UpdateItemDto updateDtO) {
        Item updatedItem = itemService.updateItem(id, updateDtO);

        return ResponseEntity.ok()
                .body(updatedItem);
    }*/

    @PostMapping(value = "/recipe/new/{itemId}")
    public String recipeUpdate(@Valid ItemFormDto itemFormDto,
                               BindingResult bindingResult, Model model,
                               @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()){
            return "content/recipeForm";
        }
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "상품 이미지를 넣어주세요.");
            return "content/recipeForm";
        }
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "content/recipeForm";
        }
        return "redirect:/";
    }






}
