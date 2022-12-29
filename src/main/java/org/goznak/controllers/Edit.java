package org.goznak.controllers;

import jakarta.validation.Valid;
import org.goznak.models.Authority;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
public class Edit {
    final
    UserService userService;
    final
    AuthorityService authorityService;
    final
    SystemService systemService;
    final
    SubSystemService subSystemService;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;
    public Edit(UserService userService, AuthorityService authorityService, SystemService systemService, SubSystemService subSystemService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.systemService = systemService;
        this.subSystemService = subSystemService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/system/{id}")
    public String editSystem(@PathVariable int id, Model model){
        model.addAttribute("system", systemService.findById(id));
        return "edit/system";
    }
    @PatchMapping("/system/{id}")
    public String updateSystem(@ModelAttribute @Valid System system, BindingResult bindingResult,
                            Model model, @PathVariable int id){
        model.addAttribute("system", system);
        system.setSystemService(systemService);
        if(bindingResult.hasErrors() || system.systemExist()){
            return "edit/system";
        }
        System systemForUpdate = systemService.findById(id);
        systemForUpdate.setName(system.getName());
        systemService.save(systemForUpdate);
        return "redirect:/search/systems";
    }
    @DeleteMapping("/system/{id}")
    public String deleteSystem(@PathVariable int id){
        System systemForDelete = systemService.findById(id);
        systemService.delete(systemForDelete);
        return "redirect:/search/systems";
    }
    @GetMapping("/sub_system/{id}")
    public String editSubSystem(@PathVariable int id, Model model){
        SubSystem subSystem = subSystemService.findById(id);
        subSystem.updateSystemName();
        model.addAttribute("subSystem", subSystem);
        model.addAttribute("systems", systemService.findAll());
        return "edit/sub_system";
    }
    @PatchMapping("/sub_system/{id}")
    public String updateSubSystem(@ModelAttribute @Valid SubSystem subSystem, BindingResult bindingResult,
                                  Model model, @PathVariable int id){
        model.addAttribute("subSystem", subSystem);
        model.addAttribute("systems", systemService.findAll());
        subSystem.setSubSystemService(subSystemService);
        subSystem.setSystemService(systemService);
        if(bindingResult.hasErrors() || subSystem.subSystemExist() || subSystem.systemNotExist()){
            return "edit/sub_system";
        }
        SubSystem subSystemForUpdate = subSystemService.findById(id);
        subSystemForUpdate.setName(subSystem.getName());
        subSystemForUpdate.setSystem(systemService.findFirstByName(subSystem.getSystemName()));
        subSystemService.save(subSystemForUpdate);
        return "redirect:/search/sub_systems";
    }
    @DeleteMapping("/sub_system/{id}")
    public String deleteSubSystem(@PathVariable int id){
        SubSystem subSystemForDelete = subSystemService.findById(id);
        subSystemService.delete(subSystemForDelete);
        return "redirect:/search/sub_systems";
    }
    @GetMapping("/user/{username}")
    public String editUser(@PathVariable String username, Model model){
        User user = userService.findById(username);
        Authority authority = authorityService.findById(username);
        user.setPassword("");
        user.setRole(authority.getAuthority().toString());
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.getRolesNameList());
        return "edit/user";
    }
    @PatchMapping("/user/{username}")
    public String updateUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                             Model model, @PathVariable String username){
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.getRolesNameList());
        user.setUserService(userService);
        if(bindingResult.hasErrors() || user.passwordNotMatch()){
            return "edit/user";
        }
        User userForUpdate = userService.findById(username);
        userForUpdate.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userForUpdate.setEnabled(user.isEnabled());
        userForUpdate.setRole(user.getRole());
        userService.save(userForUpdate);
        authorityService.save(user.getAuthority());
        return "redirect:/search/users";
    }
    @DeleteMapping("/user/{username}")
    public String deleteUser(@PathVariable String username){
        User userForDelete = userService.findById(username);
        Authority authorityForDelete = authorityService.findById(userForDelete.getUsername());
        authorityService.delete(authorityForDelete);
        userService.delete(userForDelete);
        return "redirect:/search/users";
    }
}
