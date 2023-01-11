package org.goznak.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.goznak.models.PassSlice;
import org.goznak.models.System;
import org.goznak.models.SubSystem;
import org.goznak.models.User;
import org.goznak.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class Search {
    private final int TF_USER = 0;
    private final int TF_SYSTEM = 1;
    private final int TF_SUB_SYSTEM = 2;
    private final int TF_MAIN = 3;
    private final int TF_SOFT = 4;
    private final int TF_HISTORY = 5;
    final
    int NUM_OF_VISIBLE_ROWS = 5;
    final
    UserService userService;
    final
    SystemService systemService;
    final
    SubSystemService subSystemService;
    final
    PassSliceService passSliceService;

    public Search(UserService userService, SystemService systemService, SubSystemService subSystemService, PassSliceService passSliceService) {
        this.userService = userService;
        this.systemService = systemService;
        this.subSystemService = subSystemService;
        this.passSliceService = passSliceService;
    }

    @GetMapping()
    public String search(Model model, HttpSession session, HttpServletRequest request){
        String filter = getFilter(session, request, TF_MAIN);
        List<SubSystem> subSystems;
        if(filter == null) {
            subSystems = subSystemService.findAll();
        } else {
            subSystems = subSystemService.findByFilter(filter);
            session.setAttribute("filter", filter);
        }
        return commonSearch(model, session, request, subSystems, "search/index", "search");
    }
    @GetMapping("/users")
    public String userSearch(Model model, HttpSession session, HttpServletRequest request){
        String filter = getFilter(session, request, TF_USER);
        List<User> users;
        if(filter == null) {
            users = userService.findAll();
        } else {
            users = userService.findByFilter(filter);
            session.setAttribute("filter", filter);
        }
        return commonSearch(model, session, request, users, "search/users", "search/users");
    }
    @GetMapping("/systems")
    public String systemSearch(Model model, HttpSession session, HttpServletRequest request){
        String filter = getFilter(session, request, TF_SYSTEM);
        List<System> systems;
        if(filter == null) {
            systems = systemService.findAll();
        } else {
            systems = systemService.findByFilter(filter);
            session.setAttribute("filter", filter);
        }
        return commonSearch(model, session, request, systems, "search/systems", "search/systems");
    }
    @GetMapping("/sub_systems")
    public String subSystemSearch(Model model, HttpSession session, HttpServletRequest request){
        String filter = getFilter(session, request, TF_SUB_SYSTEM);
        List<SubSystem> subSystems;
        if(filter == null) {
            subSystems = subSystemService.findAll();
        } else {
            subSystems = subSystemService.findByFilter(filter);
            session.setAttribute("filter", filter);
        }
        return commonSearch(model, session, request, subSystems, "search/sub_systems", "search/sub_systems");
    }
    @GetMapping("/{id}")
    public String softSearch(Model model, HttpSession session, HttpServletRequest request, @PathVariable int id){
        model.addAttribute("subSystem", subSystemService.findById(id));
        String filter = getFilter(session, request, TF_SOFT);
        List<PassSlice> passSlices;
        if(filter == null) {
            passSlices = passSliceService.findBySubSystemId(id);
        } else {
            passSlices = passSliceService.findByFilter(filter, id);
            session.setAttribute("filter", filter);
        }
        return commonSearch(model, session, request, passSlices, "search/softs", "search/" + id);
    }
    @GetMapping("/history/{id}")
    public String softHistorySearch(Model model, HttpSession session, HttpServletRequest request, @PathVariable long id){
        PassSlice passSlice = passSliceService.findById(id);
        model.addAttribute("subSystem", passSlice.getSubSystem());
        List<PassSlice> passSlices;
        passSlices = passSliceService.findByName(passSlice.getSoftName());
        return commonSearch(model, session, request, passSlices, "search/history", "search/history/" + id);
    }
    private String commonSearch(Model model, HttpSession session, HttpServletRequest request,
                                CommonService<?, ?> commonService, String templatePath, String path, int targetFlag) {
        int page;
        List<?> targets;
        String pagePar = request.getParameter("page");
        String filter = request.getParameter("filter");
        if(filter == null) {
            Object tf = session.getAttribute("targetFlag");
            if(tf == null || (int) tf != targetFlag){
                session.setAttribute("targetFlag", targetFlag);
                session.setAttribute("filter", null);
            } else {
                filter = (String) session.getAttribute("filter");
            }
        }
        if(pagePar == null){
            if(filter == null) {
                targets = commonService.findAll();
            } else {
                targets = commonService.findByFilter(filter);
                session.setAttribute("filter", filter);
            }
            session.setAttribute("targets", targets);
            page = 1;
        } else {
            targets = (List<?>) session.getAttribute("targets");
            if(targets == null){
                return "redirect:/" + path;
            }
            page = Integer.parseInt(pagePar);
        }
        int numOfPages = getNumOfPages(targets.size());
        page = limit(page, 1, numOfPages);
        int offset = (page - 1) * NUM_OF_VISIBLE_ROWS;
        int end = offset + NUM_OF_VISIBLE_ROWS;
        end = Math.min(end, targets.size());
        List<?> sublist;
        if(targets.isEmpty()){
            sublist = targets;
        } else {
            sublist = targets.subList(offset, end);
        }
        model.addAttribute("targets", sublist);
        model.addAttribute("page", page);
        model.addAttribute("numOfPages", numOfPages);
        return templatePath;
    }
    private String commonSearch(Model model, HttpSession session, HttpServletRequest request,
                                List<?> targets, String templatePath, String path) {
        int page;
        String pagePar = request.getParameter("page");
        if(pagePar == null){
            session.setAttribute("targets", targets);
            page = 1;
        } else {
            targets = (List<?>) session.getAttribute("targets");
            if(targets == null){
                return "redirect:/" + path;
            }
            page = Integer.parseInt(pagePar);
        }
        int numOfPages = getNumOfPages(targets.size());
        page = limit(page, 1, numOfPages);
        int offset = (page - 1) * NUM_OF_VISIBLE_ROWS;
        int end = offset + NUM_OF_VISIBLE_ROWS;
        end = Math.min(end, targets.size());
        List<?> sublist;
        if(targets.isEmpty()){
            sublist = targets;
        } else {
            sublist = targets.subList(offset, end);
        }
        model.addAttribute("targets", sublist);
        model.addAttribute("page", page);
        model.addAttribute("numOfPages", numOfPages);
        return templatePath;
    }
    private String getFilter(HttpSession session, HttpServletRequest request, int targetFlag){
        String filter = request.getParameter("filter");
        if(filter == null) {
            Object tf = session.getAttribute("targetFlag");
            if(tf == null || (int) tf != targetFlag){
                session.setAttribute("targetFlag", targetFlag);
                session.setAttribute("filter", null);
                return null;
            } else {
                return (String) session.getAttribute("filter");
            }
        }
        return filter;
    }
    private int getNumOfPages(int size){
        int increment = size % NUM_OF_VISIBLE_ROWS > 0? 1: 0;
        int result = size / NUM_OF_VISIBLE_ROWS + increment;
        return result == 0? 1: result;
    }
    private int limit(int value, int down,int up){
        return Math.min(Math.max(value, down), up);
    }
}
