package com.shulianxunying.haierLuceneWeb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChrisLee.
 */
@RestController
@RequestMapping("/xunying")
public class DemoController {
    @RequestMapping("/searchCombineList")
    public ControllerModel searchCombineList(){
        return new ControllerModel();
    }
}
