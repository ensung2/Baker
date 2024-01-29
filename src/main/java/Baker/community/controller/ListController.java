package Baker.community.controller;

import Baker.community.dto.ListDto;
import Baker.community.dto.ViewItemDto;
import Baker.community.entity.Item;
import Baker.community.service.ItemService;
import Baker.community.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ListService listService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public String getItems(Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Item> paging = itemService.getList(page);
        model.addAttribute("paging", paging);

        List<ListDto> list = itemService.findAll()
                .stream()
                .map(ListDto::new)
                .toList();
        model.addAttribute("list", list);

        return "content/recipeList";
    }

    @GetMapping("/list/{id}")
    @PreAuthorize("hasRole('USER')")
    public String getItem(@PathVariable Long id, Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", new ViewItemDto(item));

        return "content/recipeNew";

    }

    @GetMapping("/new_recipe")
    @PreAuthorize("hasRole('USER')")
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
