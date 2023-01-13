package org.goznak.controllers;

import org.goznak.models.PassSlice;
import org.goznak.services.PassSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Other {
    final
    PassSliceService passSliceService;

    public Other(PassSliceService passSliceService) {
        this.passSliceService = passSliceService;
    }

    @GetMapping("/get_pass{id}")
    String getPassword(@PathVariable long id){
        PassSlice passSlice = passSliceService.findById(id);
        return passSlice.getPassword();
    }
}
