package com.alinopy.web;

import com.alinopy.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Snow on 2017/5/22.
 */
@Controller
@RequestMapping(value = "/outorder")
public class OutOrderController {
    @Autowired
    protected OutOrderRepository outOrderRepository;
    @Autowired
    protected ElementRepository elementRepository;
    @Autowired
    protected SupplyRepository supplyRepository;
    @Autowired
    protected OutOrderDetailRepository outOrderDetailRepository;
    @Autowired
    protected ReportRepository reportRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "9") Integer size){
        //分页查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = outOrderRepository.count();
        int pageCount =  (int)Math.ceil((double)count/size);
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }
        Page<OutOrder> outOrders = outOrderRepository.findAll(pageable);

        modelMap.addAttribute("page",page);
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("isFirstPage",isFirstPage);
        modelMap.addAttribute("isLastPage",isLastPage);
        modelMap.addAttribute("pageCount",pageCount);
        modelMap.addAttribute("count",count+1);
        modelMap.addAttribute("outOrders",outOrders);
        return "outorder";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String inorderCheck(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "9") Integer size){
        //分页查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page-1, size, sort);
        Long count = outOrderRepository.count();
        int pageCount =  (int)Math.ceil((double)count/size);
        boolean isFirstPage =false;
        boolean isLastPage =false;
        if(page==1){
            isFirstPage = true;
        }
        if(page==pageCount){
            isLastPage =true;
        }
        Page<OutOrder> outOrders = outOrderRepository.findAll(pageable);

        modelMap.addAttribute("page",page);
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("isFirstPage",isFirstPage);
        modelMap.addAttribute("isLastPage",isLastPage);
        modelMap.addAttribute("pageCount",pageCount);
        modelMap.addAttribute("count",count+1);
        modelMap.addAttribute("outOrders",outOrders);
        return "outorder-check";
    }
//
    @RequestMapping(value="/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap updateStatus(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status){
        ModelMap modelMap = new ModelMap();
        System.out.println(id);
        OutOrder outOrder = outOrderRepository.findOne(id);
        if(status==1){
            outOrder.setStatus("已通过审核");
            addReport(outOrder,"通过审核");
        }else if (status==0){
            outOrder.setStatus("未通过审核");
            addReport(outOrder,"未通过审核");
        }else if (status==2){
            outOrder.setStatus("已出库");
            addReport(outOrder,"已出库");
            List<OutOrderDetail> outOrderDetails = outOrder.getOrderDetailList();
            for (OutOrderDetail outOrderDetail: outOrderDetails){
                Element element = outOrderDetail.getElement();
                int amount = element.getAmount();
                amount-=outOrderDetail.getAmount();
                element.setAmount(amount);
                elementRepository.save(element);
            }
        }
        try{
            if(outOrderRepository.save(outOrder)!=null){
                modelMap.addAttribute("result","success");
            }else {
                modelMap.addAttribute("result","error");
            }
        }catch (Exception e){
            modelMap.addAttribute("result","exception");
        }
        return modelMap;
    }
    private Boolean addReport(OutOrder outOrder,String action){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Report report = new Report(new Date(),outOrder.getOrderNo(),userDetails.getUsername(),action,outOrder.getTotalFee());
        if(reportRepository.save(report)!=null){
            return true;
        }
        return false;
    }
//
//
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public String inOrderDetail(@PathVariable Long id, ModelMap modelMap){
        OutOrder outOrder = outOrderRepository.findOne(id);
        List<OutOrderDetail> outOrderDetails = outOrder.getOrderDetailList();
        modelMap.addAttribute("outOrder",outOrder);
        modelMap.addAttribute("outOrderDetails",outOrderDetails);
        return "outorder-detail";
    }
//
    @RequestMapping(value="/add",method = RequestMethod.GET)
    public String addOutOrder(ModelMap modelMap){
        List<Element> elements = elementRepository.findByAmountGreaterThanAndDisabledFalse(0);
        modelMap.addAttribute("elements",elements);
        return "outorder-add";
    }
//
//    @RequestMapping(value="/add/getElements",method = RequestMethod.POST)
//    @ResponseBody
//    public ModelMap getElements(@RequestParam(name = "supplyId") Long supplyId){
//        ModelMap modelMap = new ModelMap();
//        List<Element> elements;
//        if(supplyId!=0) {
//            Supply supply = supplyRepository.findOne(supplyId);
//            elements = supply.getElements();
//        }else{
//            elements = elementRepository.findAll();
//        }
//        modelMap.addAttribute("elements",elements);
//        return modelMap;
//    }
    //ajax添加订单
    @RequestMapping(value="/add/addElements",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ModelMap addElements(@RequestParam(name = "elements[]") Long[] elements,@RequestParam(name = "counts[]") Integer[] counts,@RequestParam(name = "discounts[]") Float[] discounts,@RequestParam(name = "totalFee") Double totalFee,@RequestParam(name = "contact") String contact,@RequestParam(name = "costumer") String costumer){
        ModelMap modelMap = new ModelMap();
        try{
            List<OutOrderDetail> outOrderDetails = new ArrayList<>();
            OutOrder outOrder = new OutOrder(new Date(), getOrderNo(), totalFee, costumer,contact,"待审核",outOrderDetails,"");
            for (int i=0;i<elements.length;++i){
                OutOrderDetail outOrderDetail = new OutOrderDetail(elementRepository.findOne(elements[i]),outOrder,counts[i],(double)discounts[i]);
                outOrderDetails.add(outOrderDetail);
            }
            outOrderDetailRepository.save(outOrderDetails);
            outOrder.setOrderDetailList(outOrderDetails);
            if(outOrderRepository.save(outOrder)!=null){
                modelMap.addAttribute("result","success");
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
                Report report = new Report(new Date(),outOrder.getOrderNo(),userDetails.getUsername(),"加入销售清单",outOrder.getTotalFee());
                reportRepository.save(report);
            }else {
                modelMap.addAttribute("result","error");
            }
        }catch (Exception e){
            System.out.println(e);
            modelMap.addAttribute("result","exception");
        }
        return modelMap;
    }

    private String getOrderNo(){
        Date now = new Date();
        Date startDate = new Date(now.getYear(),now.getMonth(),now.getDate());
        Date endDate = new Date(now.getYear(),now.getMonth(),now.getDate()+1);
        Integer count = outOrderRepository.countByCreateTimeBetween(startDate,endDate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String orderNo = "out_"+sdf.format(startDate);
        orderNo+=String.format("%04d", count+1);
        return orderNo;
    }

}
