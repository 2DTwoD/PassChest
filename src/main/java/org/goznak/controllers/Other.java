package org.goznak.controllers;

import org.goznak.models.PassSlice;
import org.goznak.services.PassSliceService;
import org.goznak.utils.CipherUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class Other {
    final
    PassSliceService passSliceService;
    final
    CipherUtil cipherUtil;
    public Other(PassSliceService passSliceService, CipherUtil cipherUtil) {
        this.passSliceService = passSliceService;
        this.cipherUtil = cipherUtil;
    }

    @GetMapping("/")
    String titlePage(Model model){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour <= 4 || hour >= 23){
            model.addAttribute("greeting", "Доброй ночи");
        } else if(hour <= 10){
            model.addAttribute("greeting", "Доброе утро");
        } else if(hour <= 15){
            model.addAttribute("greeting", "Добрый день");
        } else {
            model.addAttribute("greeting", "Добрый вечер");
        }
        return "index";
    }
    @ResponseBody
    @GetMapping("/get_pass/{id}")
    String getPassword(@PathVariable long id) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String result = "";
        try {
            PassSlice passSlice = passSliceService.findById(id);
            result = cipherUtil.decryptPass(passSlice.getPassword());
        }
        catch (Exception e){
            result = "Произошла ошибка, поробуйте ещё раз";
        }
        return result;
    }
}
