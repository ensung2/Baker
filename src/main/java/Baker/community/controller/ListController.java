package Baker.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list")
public class ListController {

    @GetMapping
    public String listPage() {
        return "content/recipeList";
    }

//    @GetMapping(value = "/ex02")
//    public String itemList(Model model, ItemDto itemDto) {
//        List<ItemDto> itemDtoList = new ArrayList<>();
//
//        for (int i=1; i<=10; i++) {
//            ListDto listDto = new ListDto();
//            listDto.setItemType(itemDto.getItemType());
//            listDto.setItemName(itemDto.getItemName());
//            listDto.setRegTime(LocalDateTime.now());
//
//            itemDtoList.add(itemDto);
//        }
//        model.addAttribute("itemDtoList", itemDtoList);
//        return "content/recipeList";
//
//    }
}
