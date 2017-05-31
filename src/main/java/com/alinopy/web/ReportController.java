package com.alinopy.web;

import com.alinopy.domain.Element;
import com.alinopy.domain.ElementRepository;
import com.alinopy.domain.Report;
import com.alinopy.domain.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Snow on 2017/4/6.
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ElementRepository elementRepository;

    @RequestMapping("")
    public String index(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = reportRepository.count();
        int pageCount =  (int)Math.ceil((double)count/size);
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }
        Page<Report> reports = reportRepository.findAll(pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("isFirstPage",isFirstPage);
        modelMap.addAttribute("isLastPage",isLastPage);
        modelMap.addAttribute("pageCount",pageCount);
        modelMap.addAttribute("count",count+1);
        modelMap.addAttribute("reports",reports);
        return "report";
    }
    @RequestMapping("/list")
    public String list(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        //分页查询
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = elementRepository.countByDisabledIsFalseAndAmountGreaterThan(0);
        int pageCount =  (int)Math.ceil((double)count/size);
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }

        Page<Element> elements = elementRepository.findByAmountGreaterThanAndDisabledFalse(0,pageable);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("isFirstPage",isFirstPage);
        modelMap.addAttribute("isLastPage",isLastPage);
        modelMap.addAttribute("pageCount",pageCount);
        modelMap.addAttribute("elements",elements);
        return "report-list";
    }
}
