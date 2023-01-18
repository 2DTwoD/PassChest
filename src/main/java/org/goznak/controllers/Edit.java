package org.goznak.controllers;

import jakarta.validation.Valid;
import org.goznak.models.*;
import org.goznak.models.System;
import org.goznak.services.*;
import org.goznak.utils.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    PassSliceService passSliceService;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;
    public Edit(UserService userService, AuthorityService authorityService, SystemService systemService, SubSystemService subSystemService, PassSliceService passSliceService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.systemService = systemService;
        this.subSystemService = subSystemService;
        this.passSliceService = passSliceService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @GetMapping
    public String edit(){
        return "edit/index";
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
        model.addAttribute("oldUser", null);
        model.addAttribute("roles", Roles.getRolesNameList());
        return "edit/user";
    }
    @PatchMapping("/user/{username}")
    public String updateUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                             Model model, Authentication authentication, @PathVariable String username){
        User oldUser = userService.findById(username);
        Authority authority = authorityService.findById(username);
        oldUser.setRole(authority.getAuthority().toString());
        model.addAttribute("user", user);
        model.addAttribute("oldUser", oldUser);
        model.addAttribute("roles", Roles.getRolesNameList());
        user.setUserService(userService);
        user.setAuthentication(authentication);
        if(bindingResult.hasErrors() || user.passwordNotMatch() || user.noEditUser(oldUser)){
            return "edit/user";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
        authorityService.save(user.getAuthority());
        return "redirect:/search/users";
    }
    @DeleteMapping("/user/{username}")
    public String deleteUser(@PathVariable String username, Model model, Authentication authentication){
        User user = userService.findById(username);
        Authority authority = authorityService.findById(username);
        model.addAttribute("user", user);
        model.addAttribute("oldUser", null);
        model.addAttribute("roles", Roles.getRolesNameList());
        user.setUserService(userService);
        user.setAuthentication(authentication);
        user.setPasswordConfirm("");
        user.setRole(authority.getAuthority().toString());
        if(user.noDeleteUser()){
            user.setPassword("");
            return "edit/user";
        }
        authorityService.delete(authority);
        userService.delete(user);
        return "redirect:/search/users";
    }
    @GetMapping("/soft/{id}")
    public String editSoft(@PathVariable long id, Model model){
        PassSlice passSlice = passSliceService.findById(id);
        model.addAttribute("passSlice", passSlice);
        model.addAttribute("rolesForCredentials", Roles.rolesForCredentials);
        return "edit/soft";
    }
    @PatchMapping("/soft/{id}")
    public String updateSoft(@ModelAttribute @Valid PassSlice passSlice, BindingResult bindingResult,
                             Model model, @PathVariable long id, Authentication authentication){
        PassSlice oldPassSlice = passSliceService.findById(id);
        SubSystem subSystem = oldPassSlice.getSubSystem();
        model.addAttribute("passSLice", passSlice);
        model.addAttribute("rolesForCredentials", Roles.rolesForCredentials);
        passSlice.setPassSliceService(passSliceService);
        passSlice.setSoftName(oldPassSlice.getSoftName());
        passSlice.setSubSystem(subSystem);
        if(bindingResult.hasErrors() || passSlice.softExist() || passSlice.noChange()){
            return "edit/soft";
        }
        PassSlice newPassSlice = new PassSlice();
        newPassSlice.setSubSystem(subSystem);
        newPassSlice.setLogin(passSlice.getLogin());
        newPassSlice.setPassword(passSlice.getPassword());
        newPassSlice.setLastChange(new Date());
        newPassSlice.setUser(userService.getCurrentUser(authentication));
        newPassSlice.setSoftName(oldPassSlice.getSoftName());
        newPassSlice.setRole(passSlice.getRole());
        newPassSlice.setActual(true);
        newPassSlice.setCredentialsIds(oldPassSlice.getCredentialsIds());
        oldPassSlice.setActual(false);
        passSliceService.save(oldPassSlice);
        passSliceService.save(newPassSlice);
        return "redirect:/search/" + subSystem.getId();
    }
    @DeleteMapping("/soft/{id}")
    public String deleteSoft(@PathVariable long id){
        PassSlice passSliceForDelete = passSliceService.findById(id);
        passSliceService.deleteAll(passSliceForDelete.getSoftName(), passSliceForDelete.getSubSystemId());
        return "redirect:/search/" + passSliceForDelete.getSubSystemId();
    }
}
