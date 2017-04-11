package com.alinopy.web;

import com.alinopy.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Snow on 2017/4/6.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("login")
    public String login(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "login";
    }

    @RequestMapping("index/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

    @RequestMapping(value = "login/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doLogin(@RequestParam("username") String username, @RequestParam("password") String pwd) {
        ModelMap map = new ModelMap();
        if (userRepository.findUserByNameAndPwd(username, pwd) != null) {
            map.addAttribute("result", SUCCESS);
        } else {
            map.addAttribute("result", ERROR);
        }
        return map;
    }
}
