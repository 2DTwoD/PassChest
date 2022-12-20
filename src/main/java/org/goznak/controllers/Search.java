package org.goznak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class Search {
//    @Autowired
//    UserService userService;
    @GetMapping()
    public String search(){
        return "search";
    }
}
