package Baker.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/you_recommend")
public class youtubeController {

    @GetMapping("/")
    public String youRcommend() {
        return "/content/youtubeRecommend";
    }

}
