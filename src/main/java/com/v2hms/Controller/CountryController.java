package com.v2hms.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/country")
public class CountryController {
    @PostMapping
    public String addcountry(){
        return "added";
    }

}
