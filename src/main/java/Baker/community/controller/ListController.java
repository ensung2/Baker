package Baker.community.controller;

import Baker.community.dto.ListDto;
import Baker.community.dto.ViewItemDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final ItemService itemService;

    @GetMapping("/list")
    public String getItems(Model model) {
        List<ListDto> list = itemService.findAll()
                .stream()
                .map(ListDto::new)
                .toList();
        model.addAttribute("list", list);

        return "content/recipeList";
    }

    @GetMapping("/list/{id}")
    public String getItem(@PathVariable Long id, Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", new ViewItemDto(item));

        return "content/recipeNew";

    }

    @GetMapping("/new_recipe")
    public String newRecipe(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("item", new ViewItemDto());
        }else {
            Item item = itemService.findById(id);
            model.addAttribute("item", new ViewItemDto(item));
        }

        return "content/recipeForm";
    }

//    @GetMapping
//    public String listPage() {
//        return "content/recipeList";
//    }


//    @PostMapping(value="/{itemId})
//    public String itemList(Model model,Item item) {
//        List<ItemFormDto> itemDtoList = new ArrayList<>();
//
//            ItemDto itemDto = new ItemDto();
//            itemDto.setItemType(item.getItemType());
//            itemDto.setItemName(item.getItemName());
//            itemDto.setRegTime(LocalDateTime.now());
//
//            itemDtoList.add(itemDto);
//
//        model.addAttribute("itemDtoList", itemDtoList);
//        return "content/recipeList";
//
//    }
}
