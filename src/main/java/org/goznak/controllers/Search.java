package org.goznak.controllers;

import org.goznak.services.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class Search {
    final
    SystemService systemService;

    public Search(SystemService systemService) {
        this.systemService = systemService;
    }

    @GetMapping()
    public String search(){
        return "search";
    }
    @GetMapping("/systems")
    public String systemSearch(Model model){
        model.addAttribute("systems", systemService.findAll());
        return "search/systems";
    }
}
