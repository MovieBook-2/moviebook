package com.korea.MOVIEBOOK;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class main {
    @GetMapping("/")
    public String test(){
        return "layout";
    }
    @GetMapping("/2")
    public String test2(){
        return "layout2";
    }
}
