package org.goznak.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.goznak.models.System;
import org.goznak.services.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class Search {
    final
    int NUM_OF_VISIBLE_ROWS = 2;
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
    @SuppressWarnings("unchecked")
    public String systemSearch(Model model, HttpSession session, HttpServletRequest request){
        int page;
        List<System> systems;
        String pagePar = request.getParameter("page");
        String filter = request.getParameter("filter");
        if(pagePar == null){
            if(filter == null) {
                systems = systemService.findAll();
            } else {
                systems = systemService.findByName(filter);
            }
            session.setAttribute("systems", systems);
            page = 1;
        } else {
            systems = (List<System>) session.getAttribute("systems");
            if(systems == null){
                return "redirect:/search/systems";
            }
            page = Integer.parseInt(pagePar);
        }
        int numOfPages = (int) Math.round((double) systems.size() / NUM_OF_VISIBLE_ROWS);
        page = Math.max(page, 1);
        page = Math.min(page, numOfPages);
        int offset = (page - 1) * NUM_OF_VISIBLE_ROWS;
        int end = offset + NUM_OF_VISIBLE_ROWS;
        end = Math.min(end, systems.size());
        model.addAttribute("systems", systems.subList(offset, end));
        model.addAttribute("page", page);
        model.addAttribute("numOfPages", numOfPages);
        return "search/systems";
    }
}
