package Baker.community.controller;

import Baker.community.dto.AddItemDto;
import Baker.community.dto.NewItemDto;
import Baker.community.dto.UpdateItemDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/recipe")
    public String recipeForm() {
        return "content/recipeForm";
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Item> addItem(@RequestBody AddItemDto addItemDto) {
        Item savedItem = itemService.savedItem(addItemDto);
        /**
         * 200 OK : 요청이 성공적으로 수행되었음
         * 201 CREATED : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
         * 400 BAD REQUEST : 요청 값이 잘못되어 요청에 실패했음
         * 403 FORBIDDEN : 권한이 없어 요청에 실패했음
         * 404 NOT FOUND : 요청 값으로 찾은 리소스가 없어 요청에 실패했음
         * 500 INTERNAL SERVER ERROR : 서버 상에 문제가 있어 요청에 실패했음
         */
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedItem);
    }

    @GetMapping("/recipe/new")
    public ResponseEntity<List<NewItemDto>> findAllItem() {
        List<NewItemDto> newItemDtos = itemService.findAll()
                .stream()
                .map(NewItemDto::new)
                .toList();

        return ResponseEntity.ok()
                .body(newItemDtos);

    }

    @GetMapping("/recipe/new/{id}")
    public ResponseEntity<NewItemDto> findArticle(@PathVariable long id) {
        Item item = itemService.findById(id);

        return ResponseEntity.ok()
                .body(new NewItemDto(item));
    }

    @DeleteMapping("/recipe/new/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/recipe/new/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id,
                                           @RequestBody UpdateItemDto updateItem) {
        Item updatedItem = itemService.updateItem(id, updateItem);

        return ResponseEntity.ok()
                .body(updatedItem);
    }

//    @PostMapping(value = "/recipe/new")
//    public String recipeNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
//                            Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFiles){
//
//        // 상품 등록값이 없다면! return
//        if (bindingResult.hasErrors()){
//            return "content/recipeForm";
//        }
//
//        // 상품 등록 이미지가 없다면? return
//        if (itemImgFiles.get(0).isEmpty() && itemFormDto.getId() == null) {
//            model.addAttribute("errorMessage", "이미지를 첨부해 주세요.");
//            return "content/recipeForm";
//        }
//        try {
////            itemService.saveItem(itemFormDto, itemImgFiles);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "레시피 등록이 실패하였습니다.");
//            return "content/recipeForm";
//        }
//
//        return "redirect:/list";
//    }
}
