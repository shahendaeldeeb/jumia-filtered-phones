package com.jumia.phones.controller;

import com.jumia.phones.model.NormalizedPhone;
import com.jumia.phones.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/")
    public String getNormalizedPhones(Model model,
                                      @RequestParam(name = "country", required = false) String countryName,
                                      @RequestParam(name = "state", required = false) String state) {

        List<NormalizedPhone> normalizedPhoneList = phoneService.getNormalizedPhoneList(countryName, state);
        model.addAttribute("normalizedPhoneList", normalizedPhoneList);
        return "index.html";
    }

}
