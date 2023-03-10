package org.goznak.controllers;

import jakarta.validation.Valid;
import org.goznak.models.*;
import org.goznak.models.System;
import org.goznak.services.*;
import org.goznak.utils.CipherUtil;
import org.goznak.utils.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.Date;

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
    PassSliceService passSliceService;
    final
    AuthorityService authorityService;
    final
    CredentialsIdsService credentialsIdsService;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;
    final
    CipherUtil cipherUtil;

    public Add(UserService userService, SystemService systemService, SubSystemService subSystemService, PassSliceService passSliceService, AuthorityService authorityService, CredentialsIdsService credentialsIdsService, BCryptPasswordEncoder bCryptPasswordEncoder, CipherUtil cipherUtil) {
        this.passSliceService = passSliceService;
        this.subSystemService = subSystemService;
        this.authorityService = authorityService;
        this.userService = userService;
        this.systemService = systemService;
        this.credentialsIdsService = credentialsIdsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cipherUtil = cipherUtil;
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
    @GetMapping("/soft/{subSystemId}")
    public String addSoft(Model model, @PathVariable int subSystemId){
        PassSlice passSlice = new PassSlice();
        passSlice.setSubSystem(subSystemService.findById(subSystemId));
        model.addAttribute("passSlice", passSlice);
        model.addAttribute("rolesForCredentials", Roles.rolesForCredentials);
        return "add/soft";
    }
    @PostMapping("/soft/{subSystemId}")
    public String newSoft(@ModelAttribute @Valid PassSlice passSlice, BindingResult bindingResult, Model model, @PathVariable int subSystemId, Authentication authentication) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        model.addAttribute("passSlice", passSlice);
        model.addAttribute("subSystemId", subSystemId);
        model.addAttribute("rolesForCredentials", Roles.rolesForCredentials);
        passSlice.setPassSliceService(passSliceService);
        passSlice.setSubSystem(subSystemService.findById(subSystemId));
        if(bindingResult.hasErrors() || passSlice.softExist()){
            return "add/soft";
        }
        passSlice.setPassword(cipherUtil.encryptPass(passSlice.getPassword()));
        passSlice.setLastChange(new Date());
        passSlice.setUser(userService.getCurrentUser(authentication));
        passSlice.setActual(true);
        CredentialsIds credentialsIds = new CredentialsIds();
        credentialsIdsService.save(credentialsIds);
        passSlice.setCredentialsIds(credentialsIds);
        passSliceService.save(passSlice);
        return "redirect:/search/" + subSystemId;
    }
}
