package com.alinopy.web;

import com.alinopy.domain.Element;
import com.alinopy.domain.ElementRepository;
import com.alinopy.domain.Supply;
import com.alinopy.domain.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/4/10.
 */
@Controller
@RequestMapping(value = "/supply")
public class SupplyController {
    @Autowired
    protected SupplyRepository supplyRepository;
    @Autowired
    protected ElementRepository elementRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listSupply(ModelMap modelMap,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "12") Integer size) {
        //分页查询
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = supplyRepository.count();
        int pageCount =  (int)Math.ceil((double)count/size);
        Page<Supply> supplies = supplyRepository.findAll(pageable);
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }
        modelMap.addAttribute("isFirstPage", isFirstPage);
        modelMap.addAttribute("isLastPage", isLastPage);
        modelMap.addAttribute("supplies", supplies);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("pageCount", pageCount);
        modelMap.addAttribute("suppliesCount", count + 1);
        return "supply";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String supplyDetail(@PathVariable Long id, ModelMap modelMap) {
        Supply supply = supplyRepository.findOne(id);
        if (supply == null) {
            supply = new Supply("添加供应商", "供应商地址", "供应商邮箱", "供应商电话", "活动中", new ArrayList<>());
            modelMap.addAttribute("isUpdate", 0);
        } else {
            modelMap.addAttribute("isUpdate", 1);
        }
        modelMap.addAttribute("supply", supply);
        return "supply-detail";
    }

    //添加用户，使用post请求
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap addSupply(@ModelAttribute Supply supply) {
        ModelMap modelMap = tryFunc(supply, supplyRepository);
        return modelMap;
    }

    //更新用户，使用put请求
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ModelMap updateSupply(@PathVariable Long id, @ModelAttribute Supply supply) {
        supply.setId(id);
        ModelMap modelMap = tryFunc(supply, supplyRepository);
        return modelMap;
    }

    //获取供应商的商品
    @RequestMapping(value = "/elements/{id}", method = RequestMethod.GET)
    public String supplyElements(@PathVariable Long id, ModelMap modelMap) {
        Supply supply = supplyRepository.findOne(id);
        List<Element> elements = supply.getElements();
        List<Element> elementList = elementRepository.findAll();
        modelMap.addAttribute("supply", supply);
        modelMap.addAttribute("elementList", elementList);
        modelMap.addAttribute("elements", elements);
        return "supply-element";
    }

    //增加供应商对应的商品
    @RequestMapping(value = "/addElements", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap addElements(@RequestParam(name = "supplyId") Long supplyId, @RequestParam(name = "addElements[]") Long[] addElements) {
        Supply supply = supplyRepository.findOne(supplyId);
        List<Element> elements = supply.getElements();
        for (Long addElement : addElements) {
            elements.add(elementRepository.findOne(addElement));
        }
        supply.setElements(elements);
        System.out.println(elements.toString());
        ModelMap modelMap = tryFunc(supply, supplyRepository);
        modelMap.addAttribute("supplyId", supplyId);
        modelMap.addAttribute("addElements", addElements);
        return modelMap;
    }

    //删除供应商对应的商品
    @RequestMapping(value = "/removeOneElement", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap removeOneElement(@RequestParam(name = "supplyId") Long supplyId, @RequestParam(name = "elementId") Long elementId) {
        Supply supply = supplyRepository.findOne(supplyId);
        List<Element> elements = supply.getElements();
        Element element = elementRepository.findOne(elementId);
        if (elements.contains(element)) {
            elements.remove(element);
        }
        ModelMap modelMap = tryFunc(supply, supplyRepository);
        return modelMap;
    }

    //删除供应商对应的商品(多个)
    @RequestMapping(value = "/removeElements", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap removeElements(@RequestParam(name = "supplyId") Long supplyId, @RequestParam(name = "deleteElements[]") Long[] deleteElements) {
        Supply supply = supplyRepository.findOne(supplyId);
        List<Element> elements = supply.getElements();
        for (Long id: deleteElements) {
            Element element = elementRepository.findOne(id);
            if (elements.contains(element)) {
                elements.remove(element);
            }
        }
        ModelMap modelMap = tryFunc(supply, supplyRepository);
        return modelMap;
    }

    //添加、更新供应商的时候；返回相应操作的结果
    public static ModelMap tryFunc(Object object, JpaRepository repository) {
        ModelMap modelMap = new ModelMap();
        try {
            if (repository.save(object) != null) {
                modelMap.addAttribute("result", "success");
            } else {
                modelMap.addAttribute("result", "error");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            modelMap.addAttribute("result", "exception");
        }
        return modelMap;
    }
}
