package com.alinopy.web;

import com.alinopy.domain.Supply;
import com.alinopy.domain.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Snow on 2017/4/10.
 */
@Controller
@RequestMapping(value="/supply")
public class SupplyController {
    @Autowired
    protected SupplyRepository supplyRepository;

    @RequestMapping(value="/",method= RequestMethod.GET)
    public String listSupply(ModelMap modelMap){
        //分页查询
        List<Supply> supplies = supplyRepository.findAll();
        modelMap.addAttribute("supplies",supplies);
        modelMap.addAttribute("suppliesCount",supplies.size()+1);
        return "supply";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public String supplyDetail(@PathVariable Long id,ModelMap modelMap){
        Supply supply = supplyRepository.findOne(id);
        if(supply==null){
            supply = new Supply("添加供应商","供应商地址","供应商邮箱","供应商电话","活动中");
            modelMap.addAttribute("isUpdate",0);
        }else {
            modelMap.addAttribute("isUpdate",1);
        }
        modelMap.addAttribute("supply",supply);
        return "supply-detail";
    }

    //添加用户，使用post请求
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public ModelMap addSupply(@ModelAttribute Supply supply){
        ModelMap modelMap = tryFunc(supply);
        return modelMap;
    }

    //更新用户，使用put请求
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public ModelMap updateSupply(@PathVariable Long id,@ModelAttribute Supply supply){
        supply.setId(id);
        ModelMap modelMap = tryFunc(supply);
        return modelMap;
    }

    //添加、更新供应商的时候；返回相应操作的结果
    private ModelMap tryFunc(Supply supply){
        ModelMap modelMap = new ModelMap();
        try {
            if(supplyRepository.save(supply)!=null){
                modelMap.addAttribute("result","success");
            }else {
                modelMap.addAttribute("result", "error");
            }
        }catch (Exception e){
            modelMap.addAttribute("result","exception");
        }
        return modelMap;
    }
}
