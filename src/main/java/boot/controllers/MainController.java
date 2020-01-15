package boot.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController
{
    @GetMapping("/")
    public String welcome()
    {
        return "index";
    }

    @GetMapping("/add")
    public String add()
    {
        return "add";
    }

    @GetMapping("/edit")
    public String edit()
    {
        return "edit";
    }

    @GetMapping("/history")
    public String history()
    {
        return "history";
    }

    @GetMapping("/login" )
    public String login()
    {
        //Очищаем контекст безопасности при каждом запросе
        SecurityContextHolder.clearContext();
        return "login";
    }

    @GetMapping("/registration" )
    public String registration()
    {
        return "registration";
    }

    @GetMapping("/error_page" )
    public String error()
    {
        return "error_page";
    }
}
