package Baker.community.controller;

import Baker.community.dto.ItemFormDto;
import Baker.community.dto.ItemSearchDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final ItemService itemService;

/*    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getItems(Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("page", page);

        List<ListDto> list = itemService.findAll()
                .stream()
                .map(ListDto::new)
                .toList();
        model.addAttribute("list", list);

        return "content/recipeList";
    }*/


    // 상품 관리 화면 이동 및 조회한 상품 데이터를 화면에 전달
    @GetMapping(value = {"/list", "/list/{page}"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public String itemList(ItemSearchDto itemSearchDto,
                           @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
        Page<Item> items = itemService.getItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "content/recipeList";
    }

    // 레시피 목록(name) 클릭 시 레시피북 형태로 볼 수 있는 로직
    @GetMapping(value = "/recipeBook/{itemId}")
    public String recipeBook(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "content/recipeNew";
    }

/*    // 레시피 삭제
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);
        return ResponseEntity.ok()
                .build();
    }*/
}
