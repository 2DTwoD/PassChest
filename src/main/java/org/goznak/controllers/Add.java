package org.goznak.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.goznak.dao.AuthorityDAO;
import org.goznak.models.User;
import org.goznak.dao.UserDAO;
import org.goznak.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/add")
public class Add {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AuthorityDAO authorityDAO;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/system")
    public String addSystem(){
        return "add_new_system";
    }
    @GetMapping("/user")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("roles", Roles.getRolesNameList());
        model.addAttribute("user", user);
        return "add_new_user";
    }
    @PostMapping("/user")
    public String newUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.getRolesNameList());
        user.setUserDAO(userDAO);
        if(bindingResult.hasErrors() || user.passwordNotMatch() || user.userExist()){
            return "add_new_user";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userDAO.save(user);
        authorityDAO.save(user.getAuthority());
        return "redirect:/search";
    }
}
