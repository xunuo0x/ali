package com.alinopy.web;

import com.alinopy.domain.Element;
import com.alinopy.domain.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.alinopy.web.SupplyController.tryFunc;

/**
 * Created by Snow on 2017/4/16.
 */
@Controller
@RequestMapping("/element")
public class ElementController {
    @Autowired
    private ElementRepository elementRepository;

    @RequestMapping(value="/",method= RequestMethod.GET)
    public String listElements(ModelMap modelMap){
        //分页查询
        List<Element> elements = elementRepository.findAll();
        modelMap.addAttribute("elements",elements);
        modelMap.addAttribute("elementsCount",elements.size()+1);
        return "element";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public String elementDetail(@PathVariable Long id, ModelMap modelMap){
        Element element = elementRepository.findOne(id);
        if(element==null){
            element = new Element("添加零部件", 0.00, 0.00,"零部件描述","零部件类别","零部件品牌", new Date());
            modelMap.addAttribute("isUpdate",0);
        }else {
            modelMap.addAttribute("isUpdate",1);
        }
        modelMap.addAttribute("element",element);
        return "element-detail";
    }

    //添加零配件，使用post请求
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public ModelMap addSupply(@ModelAttribute Element element){
        System.out.println(element.toString());
        ModelMap modelMap = tryFunc(element,elementRepository);
        return modelMap;
    }

    //更新零配件，使用put请求
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public ModelMap updateSupply(@PathVariable Long id,@ModelAttribute Element element){
        element.setId(id);
        System.out.println(element.toString());
        ModelMap modelMap = tryFunc(element,elementRepository);
        return modelMap;
    }
}
