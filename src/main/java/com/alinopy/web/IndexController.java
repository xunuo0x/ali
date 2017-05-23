package com.alinopy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Snow on 2017/4/6.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

//    @Autowired
//    private UserRepository userRepository;
//
//    @RequestMapping(value = "login",method = RequestMethod.GET)
//    public String login(ModelMap map) {
//        // 加入一个属性，用来在模板中读取
//        map.addAttribute("host", "http://blog.didispace.com");
//        // return模板文件的名称，对应src/main/resources/templates/index.html
//        return "login";
//    }

    @RequestMapping("")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

//    @RequestMapping(value = "login", method = RequestMethod.POST)
//    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String pwd) {
//        ModelMap map = new ModelMap();
//        System.out.println(username);
//        if (userRepository.findUserByUsernameAndPassword(username, pwd) != null) {
//            map.addAttribute("result", SUCCESS);
//        } else {
//            map.addAttribute("result", ERROR);
//        }
//        return "index";
//    }
}
