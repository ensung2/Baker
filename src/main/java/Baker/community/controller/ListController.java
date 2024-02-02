package Baker.community.controller;

import Baker.community.dto.ItemSearchDto;
import Baker.community.dto.ViewItemDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemService;
import Baker.community.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final ItemService itemService;
    private final ListService listService;

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

//    @GetMapping("/list")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String getItems(Model model,
//                           @RequestParam(value = "page", defaultValue = "0") int page) {
//        model.addAttribute("page", page);
//
//        List<ListDto> list = itemService.findAll()
//                .stream()
//                .map(ListDto::new)
//                .toList();
//        model.addAttribute("list", list);
//
//        return "content/recipeList";
//    }

    // 상품 관리 화면 이동 및 조회한 상품 데이터를 화면에 전달
    @GetMapping(value = {"/list", "/list/{page}"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public String itemList(ItemSearchDto itemSearchDto,
                           @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Item> items = itemService.getItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "content/recipeList";
    }

//    @GetMapping("/list/{id}")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String getItem(@PathVariable Long id, Model model){
//        Item item = itemService.findById(id);
//        model.addAttribute("item", new ViewItemDto(item));
//
//        return "content/recipeNew";
//
//    }

    @GetMapping("/new_recipe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String newRecipe(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("item", new ViewItemDto());
        }else {
            Item item = itemService.findById(id);
            model.addAttribute("item", new ViewItemDto(item));
        }

        return "content/recipeForm";
    }
}
