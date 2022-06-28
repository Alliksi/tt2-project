package com.storage.general.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value={"/index"})
    public String redirectToIndexView() {
        return "redirect:/";
    }

    @GetMapping(value={"/"})
    public String showIndexView() {
        return "general/index";
    }
}
