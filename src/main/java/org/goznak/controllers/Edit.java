package org.goznak.controllers;

import jakarta.validation.Valid;
import org.goznak.models.System;
import org.goznak.services.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
public class Edit {
    final
    SystemService systemService;

    public Edit(SystemService systemService) {
        this.systemService = systemService;
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
}
