package com.xfu.controller;

import com.xfu.entity.ProductDetails;
import com.xfu.service.IProductService;
import com.xfu.util.JsonMsg;
import com.xfu.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService service;

    @RequestMapping("/uploadProduct.do")
    public String uploadFile(MultipartFile productFile){
        try {
          service.analysisExcel(productFile);
            return "redirect:/jsp/success.html";
        } catch (Exception e) {
            return "redirect:/jsp/error.html";
        }
    }


    @RequestMapping("/findone.do")
    @ResponseBody
    public ResultData findOneProduct(String product_id){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
       resultData.setData(service.findOneProduct(Integer.parseInt(product_id)));
        return  resultData;
    }
    @RequestMapping("/findAll.do")
    @ResponseBody
    public ResultData findAllProduct(String categoryId){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.findAllProduct(Integer.parseInt(categoryId)));
        return  resultData;
    }

    @RequestMapping("/findmrtjlist.do")
    @ResponseBody
    public ResultData findMrtjList(){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.findMrtjList());
        return  resultData;
    }

    @RequestMapping("/searchproduct.do")
    @ResponseBody
    public ResultData searchProduct(String store){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.searchProduct(store));
        return  resultData;
    }


    @RequestMapping(value = "/shopSupervise.do", method = RequestMethod.GET)
    public ModelAndView getProduct(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo){
        ModelAndView mv = new ModelAndView("shopSupervise");
        //每页显示的记录行数
        int limit = 6;
        //总记录数
        int totalItems = service.getProductCount();
        int temp = totalItems / limit;
        int totalPages = (totalItems % limit== 0) ? temp : temp+1;
        //每页的起始行(offset+1)数据，如第一页(offset=0，从第1(offset+1)行数据开始)
        int offset = (pageNo - 1)*limit;
        List<ProductDetails> plist = service.findAllProduct(offset, limit);
        mv.addObject("plist", plist)
                .addObject("totalItems", totalItems)
                .addObject("totalPages", totalPages)
                .addObject("curPageNo", pageNo);

        return mv;
    }


    @RequestMapping(value = "/deleteEmp.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg deleteEmp(@RequestParam("p_id") Integer p_id){
        int res = 0;
        if (p_id > 0){
            res = service.deletePById(p_id);
        }
        if (res != 1){
            return JsonMsg.fail().addInfo("p_del_error", "商品删除异常");
        }
        return JsonMsg.success();
    }


}
