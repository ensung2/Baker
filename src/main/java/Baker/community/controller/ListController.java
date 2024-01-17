package Baker.community.controller;

import Baker.community.dto.ItemDto;
import Baker.community.dto.ListDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/list")
public class ListController {

    @GetMapping
    public String listPage() {
        return "content/recipeList";
    }

    @GetMapping(value = "/ex02")
    public String itemList(Model model, ItemDto itemDto) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            ListDto listDto = new ListDto();
            listDto.setItemType(itemDto.getItemType());
            listDto.setItemName(itemDto.getItemName());
            listDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "content/recipeList";

    }
}
