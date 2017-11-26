package cn.sf80.weixin.manager.controller.card;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardController {

    @GetMapping("/card/card")
    public String card(){
      return "card/card";
    }
    @GetMapping("/card/card-add")
    public String cardAdd(){
        return "card/card-add";
    }

}
