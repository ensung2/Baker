package Baker.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YoutubeController {

    @GetMapping(value = "/youtube")
    public String youtube() {
        return "content/youtube";
    }

}
