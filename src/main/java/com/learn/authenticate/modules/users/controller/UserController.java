package com.learn.authenticate.modules.users.controller;

import com.learn.authenticate.modules.users.controller.validate.UserValidate;
import com.learn.authenticate.modules.users.model.component.LoginDTO;
import com.learn.authenticate.modules.users.model.component.UserAddDTO;
import com.learn.authenticate.modules.users.model.entity.User;
import com.learn.authenticate.modules.users.model.service.UserService;
import com.learn.authenticate.utils.transientObject.ValidateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService userService;
    private UserValidate userValidate;

    @Autowired
    public UserController(UserService userService, UserValidate userValidate) {
        this.userService = userService;
        this.userValidate = userValidate;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpUser(Model model, HttpSession httpSession){
        if(httpSession.getAttribute("userId") != null){
            return "redirect:/profile";
        }
        model.addAttribute("user", new User());
        return "authentication/signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginUser(Model model, HttpSession httpSession){
        if(httpSession.getAttribute("userId") != null){
            return "redirect:/profile";
        }
        model.addAttribute("user", new User());
        return "authentication/login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUpUser(@ModelAttribute UserAddDTO userAddDTO, HttpSession httpSession, Model model){
        if(httpSession.getAttribute("userId") != null){
            return "redirect:/profile";
        }
        //check email, password, phonenumber
        User user = userAddDTO.convert2Object();
        ValidateObject validateObject = this.userValidate.addNewItem(user);
        if(validateObject.getResult().equals("error")){
            model.addAttribute("errorList", validateObject.getMessages());
            return "authentication/signup";
        }
        user = this.userService.save(user);
        httpSession.setAttribute("userId" , user.getUserId());
        return "authentication/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute LoginDTO loginDTO, HttpSession httpSession){
        if(httpSession.getAttribute("userId") != null){
            return "redirect:/profile";
        }
        User user = this.userService.loginUser(loginDTO.convert2Object());
        if(user != null){
            httpSession.setAttribute("userId" , user.getUserId());
            return "redirect:/profile";
        }
        return "authentication/login";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("userId") == null){
            return "redirect:/signup";
        }
        User user = this.userService.findById((Long) httpSession.getAttribute("userId"));
        if(user == null){
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "/user/profile";
    }

}
