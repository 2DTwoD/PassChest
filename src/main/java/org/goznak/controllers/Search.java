package org.goznak.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.goznak.services.CommonService;
import org.goznak.services.SubSystemService;
import org.goznak.services.SystemService;
import org.goznak.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class Search {
    private final int TF_USER = 0;
    private final int TF_SYSTEM = 1;
    private final int TF_SUB_SYSTEM = 2;
    final
    int NUM_OF_VISIBLE_ROWS = 5;
    final
    UserService userService;
    final
    SystemService systemService;
    final
    SubSystemService subSystemService;

    public Search(UserService userService, SystemService systemService, SubSystemService subSystemService) {
        this.userService = userService;
        this.systemService = systemService;
        this.subSystemService = subSystemService;
    }

    @GetMapping()
    public String search(){
        return "search";
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
        int increment = targets.size() % NUM_OF_VISIBLE_ROWS > 0? 1: 0;
        int numOfPages = targets.size() / NUM_OF_VISIBLE_ROWS + increment;
        page = Math.max(page, 1);
        page = Math.min(page, numOfPages);
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
}
