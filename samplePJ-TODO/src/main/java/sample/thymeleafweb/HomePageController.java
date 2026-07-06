package sample.thymeleafweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    // 1. トップページ（/）へのアクセスを処理するメソッドを追加
    @GetMapping("/")
    public String index() {
        // "/home" へ転送（リダイレクト）する
        return "redirect:/home";
    }

    // 2. 元々ある "/home" の処理
    @GetMapping("/home")
    public String showHomePage() {
        return "homePage";
    }
}