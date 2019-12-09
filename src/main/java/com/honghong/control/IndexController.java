package com.honghong.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/11/27 15:49
 */
@Controller
@RequestMapping
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "forward:/login.html";
    }
}
