package Baker.community.controller;

import Baker.community.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class ItemController {

    @GetMapping(value ="/write")
    public String writeRecipe(Model model) {
        model.addAttribute("itemDto", new ItemDto());
        return "content/newRecipe";
    }
}
