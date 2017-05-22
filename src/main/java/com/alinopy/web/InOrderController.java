package com.alinopy.web;

import com.alinopy.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/5/22.
 */
@Controller
@RequestMapping(value = "/inorder")
public class InOrderController {
    @Autowired
    protected InOrderRepository inOrderRepository;
    @Autowired
    protected ElementRepository elementRepository;
    @Autowired
    protected SupplyRepository supplyRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        //分页查询
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = inOrderRepository.count();
        int pageCount =  1;
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }
        Page<InOrder> inOrders = inOrderRepository.findAll(pageable);

        modelMap.addAttribute("page",page);
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("isFirstPage",isFirstPage);
        modelMap.addAttribute("isLastPage",isLastPage);
        modelMap.addAttribute("pageCount",pageCount);
        modelMap.addAttribute("count",count+1);
        modelMap.addAttribute("inOrders",inOrders);
        return "inorder";
    }

    @RequestMapping(value="/add",method = RequestMethod.GET)
    public String addInOrder(ModelMap modelMap){
        List<Supply> supplies = supplyRepository.findAll();
        List<Element> elements = elementRepository.findAll();
        modelMap.addAttribute("elements",elements);
        modelMap.addAttribute("supplies",supplies);
        return "inorder-add";
    }

    @RequestMapping(value="/add/getElements",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getElements(@RequestParam(name = "supplyId") Long supplyId){
        ModelMap modelMap = new ModelMap();
        List<Element> elements = new ArrayList<>();
        if(supplyId!=0) {
            Supply supply = supplyRepository.findOne(supplyId);
            elements = supply.getElements();
        }else{
            elements = elementRepository.findAll();
        }
        modelMap.addAttribute("elements",elements);
        return modelMap;
    }


}
