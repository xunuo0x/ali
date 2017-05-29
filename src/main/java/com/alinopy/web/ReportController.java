package com.alinopy.web;

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

    @RequestMapping("")
    public String index(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = reportRepository.count();
        int pageCount =  1;
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
}
