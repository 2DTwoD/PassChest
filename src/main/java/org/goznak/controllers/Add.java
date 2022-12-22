package org.goznak.controllers;

import jakarta.validation.Valid;
import org.goznak.models.System;
import org.goznak.models.User;
import org.goznak.services.AuthorityService;
import org.goznak.services.SystemService;
import org.goznak.services.UserService;
import org.goznak.utils.Roles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/add")
public class Add {
    final
    UserService userService;
    final
    SystemService systemService;
    final
    AuthorityService authorityService;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Add(UserService userService, SystemService systemService, AuthorityService authorityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityService = authorityService;
        this.userService = userService;
        this.systemService = systemService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/user")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("roles", Roles.getRolesNameList());
        model.addAttribute("user", user);
        return "add/user";
    }
    @PostMapping("/user")
    public String newUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.getRolesNameList());
        user.setUserService(userService);
        if(bindingResult.hasErrors() || user.passwordNotMatch() || user.userExist()){
            return "add/user";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userService.save(user);
        authorityService.save(user.getAuthority());
        return "redirect:/search";
    }
    @GetMapping("/system")
    public String addSystem(Model model){
        System system = new System();
        model.addAttribute("system", system);
        return "add/system";
    }
    @PostMapping("/system")
    public String newSystem(@ModelAttribute @Valid System system, BindingResult bindingResult, Model model){
        model.addAttribute("system", system);
        system.setSystemService(systemService);
        if(bindingResult.hasErrors() || system.systemExist()){
            return "add/system";
        }
        systemService.save(system);
        return "redirect:/search/systems";
    }
}
