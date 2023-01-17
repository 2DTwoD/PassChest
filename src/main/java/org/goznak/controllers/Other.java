package org.goznak.controllers;

import org.goznak.models.PassSlice;
import org.goznak.services.PassSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

@Controller
public class Other {
    final
    PassSliceService passSliceService;
    public Other(PassSliceService passSliceService) {
        this.passSliceService = passSliceService;
    }

    @GetMapping("/")
    String titlePage(Model model){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour <= 4 || hour >= 23){
            model.addAttribute("greeting", "Доброй ночи");
        } else if(hour <= 11){
            model.addAttribute("greeting", "Доброе утро");
        } else if(hour <= 16){
            model.addAttribute("greeting", "Добрый день");
        } else {
            model.addAttribute("greeting", "Добрый вечер");
        }
        return "index";
    }
    @ResponseBody
    @GetMapping("/get_pass/{id}")
    String getPassword(@PathVariable long id){
        PassSlice passSlice = passSliceService.findById(id);
        return passSlice.getPassword();
    }
}
