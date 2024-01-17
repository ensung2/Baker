package Baker.community.controller;

import Baker.community.dto.ItemDto;
import Baker.community.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value ="/write")
    public String recipeForm(Model model) {
        model.addAttribute("itemDto", new ItemDto());
        return "content/recipeForm";
    }

    @PostMapping(value = "/write")
    public String recipeNew(@Valid ItemDto itemDto, BindingResult bindingResult,
                            Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFile){
        if (bindingResult.hasErrors()){
            return "content/recipeForm";
        }
        if (itemImgFile.get(0).isEmpty() && itemDto.getId() == null) {
            model.addAttribute("errorMessage", "이미지를 첨부해 주세요.");
            return "content/recipeForm";
        }
        try {
            itemService.saveItem(itemDto, itemImgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "레시피 등록이 실패되었습니다!");
            return "content/recipeForm";
        }

        return "content/recipeList";
    }


}
