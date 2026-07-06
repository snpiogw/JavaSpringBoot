package sample.thymeleafweb;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sample.common.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm() { return "register"; }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {

        if (!username.matches("^[a-zA-Z]+$") || !password.matches("^[a-zA-Z0-9]+$")) {
            model.addAttribute("errorMessage",
                    "ユーザ名は半角英字、パスワードは半角英数字で入力してください。");
            model.addAttribute("username", username);
            return "register";
        }

        try {
            loginService.registerUser(username, password);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("username", username);
            return "register";
        }

        return "redirect:/login";
    }
}