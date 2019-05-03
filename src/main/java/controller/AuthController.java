package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.UserService;

import java.util.Map;

@Controller
public class AuthController
{
    private final UserService userService;


    @Autowired
    public AuthController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login(Map<String, Object> model)
    {
        return "login";
    }

    @PostMapping("login")
    public String login(String message, Map<String, Object> model)
    {
        model.put("message", message);
        return "login";
    }

    @GetMapping("registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("registration")
    public String registration(User user, Map<String, Object> model, RedirectAttributes attr) throws Exception
    {
        if(user != null)
        {
            try
            {
                user.setActive(true);
                userService.createNewUser(user);
            }
            catch (Exception ex)
            {
                model.put("message", "Ошибка: " + ex.getMessage());
                return "registration";
            }
        }
        else
        {
            throw new Exception("Cannot reg no user! user = null");
        }
        userService.setCurrentUser(user);
        attr.addFlashAttribute("message", "Пользователь успешно зарегистрирован!");
        return "redirect:/login";
    }

    @GetMapping("user_cabinet")
    public String user_cabinet(Map<String, Object> model, RedirectAttributes attr)
    {
        model.putIfAbsent("user", userService.getCurrentUser());
        return "user_cabinet";
    }

    @PostMapping("user_cabinet")
    public String user_cabinet(String message, Map<String, Object> model)
    {
        return "user_cabinet";
    }

    @PostMapping("pass_change")
    public String pass_change(String newpassword, String againpassword, Map<String, Object> model, RedirectAttributes attr)
    {
        if(!newpassword.equals(againpassword))
        {
            attr.addFlashAttribute("message","Новый пароль и его подтверждение не совпадают!");
            return "redirect:/user_cabinet";
        }
        else
        {
            if (newpassword.isEmpty())
            {
                attr.addFlashAttribute("message", "Новый пароль не может быть пустым!");
                return "redirect:/user_cabinet";
            }
            userService.getCurrentUser().setPassword(newpassword);
            userService.saveAndFlush();
            attr.addFlashAttribute("message", "Пароль успешно изменен!");
            return "redirect:/user_cabinet";
        }
    }

}