package com.alinopy.web;

import com.alinopy.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/inorder")
public class InOrderController {
    @Autowired
    protected InOrderRepository inOrderRepository;
    @Autowired
    protected ElementRepository elementRepository;
    @Autowired
    protected SupplyRepository supplyRepository;
    @Autowired
    protected InOrderDetailRepository inOrderDetailRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "9") Integer size){
        //分页查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
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

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String inorderCheck(ModelMap modelMap, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "9") Integer size){
        //分页查询
        Sort sort = new Sort(Sort.Direction.DESC, "id");
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
        return "inorder-check";
    }

    @RequestMapping(value="/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap updateStatus(@RequestParam(name = "id") Long id,@RequestParam(name = "status") String status){
        ModelMap modelMap = new ModelMap();
        InOrder inOrder = inOrderRepository.findOne(id);
        System.out.println(id);
        if(status.equals("1")){
            inOrder.setStatus("已通过审核");
        }else if (status.equals("0")){
            inOrder.setStatus("未通过审核");
        }else if(status.equals("2")){
            inOrder.setStatus("已入库");
            List<InOrderDetail> inOrderDetails = inOrder.getInOrderDetail();
            for (InOrderDetail inOrderDetail: inOrderDetails){
                Element element = inOrderDetail.getElement();
                int amount = element.getAmount();
                amount+=inOrderDetail.getAmount();
                element.setAmount(amount);
                elementRepository.save(element);
            }
        }
        try{
            if(inOrderRepository.save(inOrder)!=null){
                modelMap.addAttribute("result","success");
            }else {
                modelMap.addAttribute("result","error");
            }
        }catch (Exception e){
            modelMap.addAttribute("result","exception");
        }
        return modelMap;
    }


    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public String inOrderDetail(@PathVariable Long id, ModelMap modelMap){
        InOrder inOrder = inOrderRepository.findOne(id);
        List<InOrderDetail> inOrderDetails = inOrder.getInOrderDetail();
        modelMap.addAttribute("inOrder",inOrder);
        modelMap.addAttribute("inOrderDetails",inOrderDetails);
        return "inorder-detail";
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
        List<Element> elements;
        if(supplyId!=0) {
            Supply supply = supplyRepository.findOne(supplyId);
            elements = supply.getElements();
        }else{
            elements = elementRepository.findAll();
        }
        modelMap.addAttribute("elements",elements);
        return modelMap;
    }
    //ajax添加订单
    @RequestMapping(value="/add/addElements",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ModelMap addElements(@RequestParam(name = "supplies[]") Long[] supplies,@RequestParam(name = "elements[]") Long[] elements,@RequestParam(name = "counts[]") Integer[] counts,@RequestParam(name = "discounts[]") Float[] discounts,@RequestParam(name = "totalFee") Double totalFee){
        ModelMap modelMap = new ModelMap();
        try{
            List<InOrderDetail> inOrderDetails = new ArrayList<>();
            InOrder inOrder = new InOrder(new Date(), getOrderNo(), 0.0,  inOrderDetails, "待审核");
            for (int i=0;i<supplies.length;++i){
                InOrderDetail inOrderDetail = new InOrderDetail(inOrder, elementRepository.findOne(elements[i]), supplyRepository.findOne(supplies[i]), counts[i], (double)discounts[i]);
                inOrderDetails.add(inOrderDetail);
            }
            inOrderDetailRepository.save(inOrderDetails);
            inOrder.setTotalFee(totalFee);
            inOrder.setInOrderDetail(inOrderDetails);
            if(inOrderRepository.save(inOrder)!=null){
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

    private String getOrderNo(){
        Date now = new Date();
        Date startDate = new Date(now.getYear(),now.getMonth(),now.getDate());
        Date endDate = new Date(now.getYear(),now.getMonth(),now.getDate()+1);
        Integer count = inOrderRepository.countByCreateTimeBetween(startDate,endDate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String orderNo = sdf.format(startDate);
        orderNo+=String.format("%04d", count+1);
        return orderNo;
    }

}
