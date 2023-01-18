package org.goznak.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.goznak.models.*;
import org.goznak.models.System;
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
    private final int USER = 0;
    private final int SYSTEM = 1;
    private final int SUB_SYSTEM = 2;
    private final int MAIN = 3;
    private final int SOFT = 4;
    private final int HISTORY = 5;
    private final int NUM_OF_VISIBLE_ROWS = 15;
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
        resetParameters(session, request, MAIN);
        String filter = getFilter(session, request, MAIN);
        List<SubSystem> subSystems;
        if(filter == null) {
            subSystems = subSystemService.findAll();
        } else {
            subSystems = subSystemService.findByFilter(filter);
        }
        return searchEngine(model, session, request, subSystems, "search/index", MAIN);
    }
    @GetMapping("/users")
    public String userSearch(Model model, HttpSession session, HttpServletRequest request){
        resetParameters(session, request, USER);
        String filter = getFilter(session, request, USER);
        List<User> users;
        if(filter == null) {
            users = userService.findAll();
        } else {
            users = userService.findByFilter(filter);
        }
        return searchEngine(model, session, request, users, "search/users", USER);
    }
    @GetMapping("/systems")
    public String systemSearch(Model model, HttpSession session, HttpServletRequest request){
        resetParameters(session, request, SYSTEM);
        String filter = getFilter(session, request, SYSTEM);
        List<System> systems;
        if(filter == null) {
            systems = systemService.findAll();
        } else {
            systems = systemService.findByFilter(filter);
        }
        return searchEngine(model, session, request, systems, "search/systems", SYSTEM);
    }
    @GetMapping("/sub_systems")
    public String subSystemSearch(Model model, HttpSession session, HttpServletRequest request){
        resetParameters(session, request, SUB_SYSTEM);
        String filter = getFilter(session, request, SUB_SYSTEM);
        List<SubSystem> subSystems;
        if(filter == null) {
            subSystems = subSystemService.findAll();
        } else {
            subSystems = subSystemService.findByFilter(filter);
        }
        return searchEngine(model, session, request, subSystems, "search/sub_systems", SUB_SYSTEM);
    }
    @GetMapping("/{id}")
    public String softSearch(Model model, HttpSession session, HttpServletRequest request, @PathVariable int id){
        resetParameters(session, request, SOFT);
        model.addAttribute("subSystem", subSystemService.findById(id));
        String filter = getFilter(session, request, SOFT);
        List<PassSlice> passSlices;
        if(filter == null) {
            passSlices = passSliceService.findBySubSystemId(id);
        } else {
            passSlices = passSliceService.findByFilter(filter, id);
        }
        starPassword(passSlices);
        return searchEngine(model, session, request, passSlices, "search/softs", SOFT);
    }
    @GetMapping("/history/{id}")
    public String softHistorySearch(Model model, HttpSession session, HttpServletRequest request, @PathVariable long id){
        resetParameters(session, request, HISTORY);
        PassSlice passSlice = passSliceService.findById(id);
        model.addAttribute("subSystem", passSlice.getSubSystem());
        List<PassSlice> passSlices;
        passSlices = passSliceService.findBySoftNameAndRole(passSlice.getSoftName(), passSlice.getCredentialsIds());
        starPassword(passSlices);
        return searchEngine(model, session, request, passSlices, "search/history", HISTORY);
    }
    private void starPassword(List<PassSlice> list){
        for(PassSlice passSlice: list){
            passSlice.setPassword("******");
        }
    }
    private void resetParameters(HttpSession session, HttpServletRequest request, int pageId){
        if(request.getParameter("resetParameters") != null){
            session.setAttribute("filter" + pageId, null);
            session.setAttribute("page" + pageId, 0);
        }
    }
    private String getFilter(HttpSession session, HttpServletRequest request, int pageId){
        String filter = request.getParameter("filter");
        if(filter == null) {
            Object objFilter = session.getAttribute("filter" + pageId);
            filter = objFilter == null? null: (String) objFilter;
        } else {
            session.setAttribute("page" + pageId, 1);
        }
        session.setAttribute("filter", filter);
        session.setAttribute("filter" + pageId, filter);
        return filter;
    }
    private String searchEngine(Model model, HttpSession session, HttpServletRequest request,
                                List<?> targets, String templatePath, int pageId) {
        session.setAttribute("targets", targets);
        String pagePar = request.getParameter("page");
        int page;
        if(pagePar == null){
            Object sessionPage = session.getAttribute("page" + pageId);
            page = sessionPage == null? 1:(int) sessionPage;
        } else {
            page = Integer.parseInt(pagePar);
        }
        session.setAttribute("page" + pageId, page);
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
    private int getNumOfPages(int size){
        int increment = size % NUM_OF_VISIBLE_ROWS > 0? 1: 0;
        int result = size / NUM_OF_VISIBLE_ROWS + increment;
        return result == 0? 1: result;
    }
    private int limit(int value, int down,int up){
        return Math.min(Math.max(value, down), up);
    }
}
