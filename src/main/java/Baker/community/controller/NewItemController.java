package Baker.community.controller;

import Baker.community.constant.ItemType;
import Baker.community.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/new")
public class NewItemController {

    @GetMapping(value = "/writeRecipe")
    public String newRecipe() {
        return "/content/writeRecipe";
    }

    @GetMapping(value = "/ex01")
    public String newRecipe01(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemType(ItemType.Bread);
        itemDto.setItemName("레시피명");
        itemDto.setInfo("레시피 간단설명");
        itemDto.setImg("../img/011_hoya.jpg");
        itemDto.setMaterial("재료");
        itemDto.setRecipe("레시피 설명");

        model.addAttribute("itemDto", itemDto);
        return "/content/newRecipe";
    }

    @GetMapping(value = "/ex02")
    public String ItemList02(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemType(ItemType.Bread);
            itemDto.setItemName("소금빵"+i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "/content/recipeList";

    }
}
