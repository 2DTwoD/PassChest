package org.goznak.controllers;

import jakarta.validation.Valid;
import org.goznak.models.SubSystem;
import org.goznak.models.System;
import org.goznak.models.User;
import org.goznak.services.AuthorityService;
import org.goznak.services.SubSystemService;
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
    SubSystemService subSystemService;
    final
    AuthorityService authorityService;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Add(UserService userService, SystemService systemService, SubSystemService subSystemService, AuthorityService authorityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.subSystemService = subSystemService;
        this.authorityService = authorityService;
        this.userService = userService;
        this.systemService = systemService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/user")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.getRolesNameList());
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
        return "redirect:/search/users";
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
    @GetMapping("/sub_system")
    public String addSubSystem(Model model){
        SubSystem subSystem = new SubSystem();
        model.addAttribute("subSystem", subSystem);
        model.addAttribute("systems", systemService.findAll());
        return "add/sub_system";
    }
    @PostMapping("/sub_system")
    public String newSubSystem(@ModelAttribute @Valid SubSystem subSystem, BindingResult bindingResult, Model model){
        model.addAttribute("subSystem", subSystem);
        model.addAttribute("systems", systemService.findAll());
        subSystem.setSubSystemService(subSystemService);
        subSystem.setSystemService(systemService);
        if(bindingResult.hasErrors() || subSystem.subSystemExist() || subSystem.systemNotExist()){
            return "add/sub_system";
        }
        subSystem.setSystem(systemService.findFirstByName(subSystem.getSystemName()));
        subSystemService.save(subSystem);
        return "redirect:/search/sub_systems";
    }
}
