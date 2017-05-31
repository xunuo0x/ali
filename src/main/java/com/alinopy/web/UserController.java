package com.alinopy.web;

import com.alinopy.domain.SysUser;
import com.alinopy.domain.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Snow on 2017/4/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserRepository sysUserRepository;

    @RequestMapping(value="",method= RequestMethod.GET)
    public String index(ModelMap modelMap){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        SysUser sysUser = sysUserRepository.findByUsername(username);
        modelMap.addAttribute("sysUser",sysUser);
        return "user";
    }

    //更新用户，使用put请求
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap updateSupply(@RequestParam(name = "id") Long id, @RequestParam(name = "newp") String newp) {
        System.out.println(id);
        ModelMap modelMap = new ModelMap();
        SysUser sysUser = sysUserRepository.findOne(id);
        sysUser.setPassword(newp);
        if(sysUserRepository.save(sysUser)!=null){
            modelMap.addAttribute("result","success");
        }else {
            modelMap.addAttribute("result","error");
        }
        return modelMap;
    }

}
