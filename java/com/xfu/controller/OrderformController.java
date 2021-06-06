package com.xfu.controller;

import com.xfu.entity.Order_infoSum;
import com.xfu.service.IOrderformService;
import com.xfu.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/orderform")
public class OrderformController {

    @Autowired
    IOrderformService service;

    @RequestMapping("/unorder.do")
    @ResponseBody
    public ResultData createOrderform(String orderdata){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.createOrderform(orderdata));
        return resultData;
    }

    @RequestMapping("/findWaitPay.do")
    @ResponseBody
    public ResultData findWaitPayList(String userid,String status){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.findWaitPayList(userid,Integer.parseInt(status)));
        return resultData;
    }

    @RequestMapping("/upstate.do")
    @ResponseBody
    public void insertAdd(String orderid,String status){
        service.upstate(orderid,Integer.parseInt(status));
        System.out.println(orderid+"':"+status);
    }

    @RequestMapping(value = "/orderInfoSum.do", method = RequestMethod.GET)
    public ModelAndView getOrderInfoSum(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        ModelAndView mv = new ModelAndView("orderInfoSum");
        //每页显示的记录行数
        int limit = 6;
        //总记录数
        int totalItems = service.getDeptCount();
        int temp = totalItems / limit;
        int totalPages = (totalItems % limit== 0) ? temp : temp+1;
        //每页的起始行(offset+1)数据，如第一页(offset=0，从第1(offset+1)行数据开始)
        int offset = (pageNo - 1)*limit;
        List<Order_infoSum> orderlist = service.getOrderInfoSum(offset, limit);
        mv.addObject("orderlist", orderlist)
                .addObject("totalItems", totalItems)
                .addObject("totalPages", totalPages)
                .addObject("curPageNo", pageNo);

        return mv;
    }
}
