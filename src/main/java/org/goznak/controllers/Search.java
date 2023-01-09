package org.goznak.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.goznak.models.PassSlice;
import org.goznak.models.SubSystem;
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
    final
    int NUM_OF_VISIBLE_ROWS = 10;
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
        return commonSearch(model, session, request, subSystemService, "search/index", TF_MAIN);
    }
    @GetMapping("/users")
    public String userSearch(Model model, HttpSession session, HttpServletRequest request){
        return commonSearch(model, session, request, userService, "search/users", TF_USER);
    }
    @GetMapping("/systems")
    public String systemSearch(Model model, HttpSession session, HttpServletRequest request){
        return commonSearch(model, session, request, systemService, "search/systems", TF_SYSTEM);
    }
    @GetMapping("/sub_systems")
    public String subSystemSearch(Model model, HttpSession session, HttpServletRequest request){
        return commonSearch(model, session, request, subSystemService, "search/sub_systems", TF_SUB_SYSTEM);
    }
    private String commonSearch(Model model, HttpSession session, HttpServletRequest request,
                                CommonService<?, ?> commonService, String path, int targetFlag) {
        int page;
        List<?> targets;
        String pagePar = request.getParameter("page");
        String filter = request.getParameter("filter");
        if(filter == null) {
            filter = (String) session.getAttribute("filter");
        }
        if(pagePar == null){
            session.setAttribute("targetFlag", targetFlag);
            if(filter == null) {
                targets = commonService.findAll();
            } else {
                targets = commonService.findByFilter(filter);
                session.setAttribute("filter", filter);
            }
            session.setAttribute("targets", targets);
            page = 1;
        } else {
            Object tf = session.getAttribute("targetFlag");
            if(tf == null || (int) tf != targetFlag){
                session.setAttribute("targetFlag", targetFlag);
                return "redirect:/" + path;
            }
            targets = (List<?>) session.getAttribute("targets");
            if(targets == null){
                return "redirect:/" + path;
            }
            page = Integer.parseInt(pagePar);
        }
        int numOfPages = getNumOfPages(targets.size());
        page = limit(page, numOfPages, 1);
        int offset = (page - 1) * NUM_OF_VISIBLE_ROWS;
        int end = offset + NUM_OF_VISIBLE_ROWS;
        end = Math.min(end, targets.size());
        List<?> sublist;
        if(targets.isEmpty()){
            sublist = targets;
        }else {
            sublist = targets.subList(offset, end);
        }
        model.addAttribute("targets", sublist);
        model.addAttribute("page", page);
        model.addAttribute("numOfPages", numOfPages);
        return path;
    }
    @GetMapping("/{id}")
    public String passwordSearch(Model model, HttpServletRequest request, @PathVariable int id){
        int page;
        String pagePar = request.getParameter("page");
        if(pagePar == null){
            page = 1;
        } else {
            page = Integer.parseInt(pagePar);
        }
        SubSystem subSystem = subSystemService.findById(id);
        List<PassSlice> passSlices = passSliceService.findBySubSystemId(id);
        int numOfPages = getNumOfPages(passSlices.size());
        model.addAttribute("subSystem", subSystem);
        model.addAttribute("system", systemService.findById(subSystem.getSystemId()));
        model.addAttribute("passSlices", passSlices);
        model.addAttribute("numOfPages", numOfPages);
        model.addAttribute("page", limit(page, numOfPages, 1));
        return "search/softs";
    }
    private int getNumOfPages(int size){
        int increment = size % NUM_OF_VISIBLE_ROWS > 0? 1: 0;
        int result = size / NUM_OF_VISIBLE_ROWS + increment;
        return result == 0? 1: result;
    }
    private int limit(int value, int up,int down){
        return Math.min(Math.max(value, down), up);
    }
}
