package Baker.community.controller;

import Baker.community.dto.ItemFormDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemImgService;
import Baker.community.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private List<MultipartFile> multipartFileList;

//    @ModelAttribute("itemTypes")
//    public ItemType[] itemTypes() {
//        return ItemType.values();
//    }

//    @PostMapping("/recipe/new")
//    public ResponseEntity<Item> addItem(@Valid @RequestBody AddItemDto addItemDto) {
//
//        Item savedItem = itemService.save(addItemDto, multipartFileList);
//        /*
//         * 200 OK : 요청이 성공적으로 수행되었음
//         * 201 CREATED : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
//         * 400 BAD REQUEST : 요청 값이 잘못되어 요청에 실패했음
//         * 403 FORBIDDEN : 권한이 없어 요청에 실패했음
//         * 404 NOT FOUND : 요청 값으로 찾은 리소스가 없어 요청에 실패했음
//         * 500 INTERNAL SERVER ERROR : 서버 상에 문제가 있어 요청에 실패했음
//         */
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(savedItem);
//    }


//    @GetMapping("/recipe/new")
//    public ResponseEntity<List<ItemDto>> findAllItem() {
//        List<ItemDto> itemDtos = itemService.findAll()
//                .stream()
//                .map(ItemDto::new)
//                .toList();
//
//        return ResponseEntity.ok()
//                .body(itemDtos);
//
//    }

//    @GetMapping("/recipe/new/{id}")
//    public ResponseEntity<ItemDto> findArticle(@PathVariable long id) {
//        Item item = itemService.findById(id);
//
//        return ResponseEntity.ok()
//                .body(new ItemDto(item));
//    }

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
            return "item/itemForm";
        }

        return "redirect:/";

    }

    // 레시피 삭제
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    // 레시피 수정
    @PutMapping("/recipe/new/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id,
                                           @Valid UpdateItemDto updateDtO) {
        Item updatedItem = itemService.updateItem(id, updateDtO);

        return ResponseEntity.ok()
                .body(updatedItem);
    }



}
