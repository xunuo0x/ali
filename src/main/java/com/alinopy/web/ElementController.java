package com.alinopy.web;

import com.alinopy.domain.Element;
import com.alinopy.domain.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.alinopy.web.SupplyController.tryFunc;

/**
 * Created by Snow on 2017/4/16.
 */
@Controller
@RequestMapping("/element")
public class ElementController {
    @Autowired
    private ElementRepository elementRepository;

    @RequestMapping(value="",method= RequestMethod.GET)
    public String listElements(ModelMap modelMap,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        //分页查询
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = elementRepository.countByDisabledIsFalse();
        Long elementsCount = elementRepository.count();
        int pageCount =  (int)Math.ceil((double)count/size);
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }

        Page<Element> elements = elementRepository.findAllByDisabledIsFalse(pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("isFirstPage",isFirstPage);
        modelMap.addAttribute("isLastPage",isLastPage);
        modelMap.addAttribute("pageCount",pageCount);
        modelMap.addAttribute("elements",elements);
        modelMap.addAttribute("elementsCount",elementsCount+1);
        return "element";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public String elementDetail(@PathVariable Long id, ModelMap modelMap){
        Element element = elementRepository.findOne(id);
        if(element==null){
            element = new Element("添加零部件", 0.00, 0.00,"零部件描述","零部件类别","零部件品牌",false, new Date(),new ArrayList<>());
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
        ModelMap modelMap = tryFunc(element,elementRepository);
        return modelMap;
    }

    //更新零配件，使用put请求
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public ModelMap updateSupply(@PathVariable Long id,@ModelAttribute Element element){
        element.setId(id);
        element.setSupplies(elementRepository.findOne(id).getSupplies());
        ModelMap modelMap = tryFunc(element,elementRepository);
        return modelMap;
    }

    //Ajax获取所有零配件信息
    @RequestMapping(value = "/getAllElements",method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getAllElements(){
        ModelMap modelMap = new ModelMap();
        List<Element> elements = elementRepository.findAll();
        System.out.println(elements);
        modelMap.addAttribute("elements",elements);
        return modelMap;
    }

    //removeOneElement
    @RequestMapping(value = "/removeOneElement",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap removeOneElement(@RequestParam(name = "elementId") Long elementId){
        Element element = elementRepository.findOne(elementId);
        element.setDisabled(true);
        ModelMap modelMap = tryFunc(element,elementRepository);
        return modelMap;
    }

    //removeElements 删除多个
    @RequestMapping(value = "/removeElements",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap removeElements(@RequestParam(name = "deleteElements[]") Long[] deleteElements){
        Set<Element> elements = new HashSet<>();
        for (Long id:deleteElements) {
            Element element = elementRepository.findOne(id);
            element.setDisabled(true);
            elements.add(element);
        }
        ModelMap modelMap = new ModelMap();
        try {
            if(elementRepository.save(elements)!=null){
                modelMap.addAttribute("result","success");
            }else {
                modelMap.addAttribute("result","error");
            }
        }catch (Exception e){
            System.out.println(e);
            modelMap.addAttribute("result","exception");
        }
        return modelMap;
    }



    //测试
    @RequestMapping(value = "/getOneElementById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Element getOneElementById(@PathVariable Long id){
        Element element = elementRepository.findOne(id);
        elementRepository.getOne(id);
        System.out.println(element.toString());
        return element;
    }

}
