package com.honghong.control;

import com.honghong.common.Login;
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
    public String login() {
        return "forward:/login.html";
    }

    @RequestMapping("/index")
    public String index() {
        return "forward:/templates/index.html";
    }
}
