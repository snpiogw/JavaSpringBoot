package sample.thymeleafweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sample.common.dao.entity.Login;
import sample.common.service.LoginService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/register")
    public String showRegisterForm() { return "register"; }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
    	if (!username.matches("^[a-zA-Z]+$") || !password.matches("^[a-zA-Z0-9]+$")) {
            return "error_page";
        }
    	loginService.registerUser(username, password);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() { return "login"; }

}