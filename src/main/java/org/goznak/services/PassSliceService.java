package org.goznak.services;

import org.goznak.dao.PassSliceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassSliceService {
    @Autowired
    PassSliceDAO passSliceDAO;
}
