package com.xfu.service.impl;

import com.xfu.entity.Product;
import com.xfu.entity.ProductDetails;
import com.xfu.entity.Product_image;
import com.xfu.mapper.IProductMapper;
import com.xfu.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;
import java.util.*;


@Service
public class ProductService implements  IProductService {

    @Autowired
    IProductMapper mapper;

    /**
     * 解析excel
     * @param toFile
     * @throws IOException
     */

@Override
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public  void analysisExcel(MultipartFile toFile) throws IOException
{
    Workbook wookbook = null;
    Sheet sheet =null;
    Map<Map<String, PictureData>,Integer>  Mapmaplist=null;
    Map<String, PictureData> maplist=null;
    List<String> prinPath=null;
    List<Product_image> imagelist = new ArrayList<>();
    try
    {        //2003版本的excel，用.xls结尾
        wookbook = new HSSFWorkbook(toFile.getInputStream());//得到工作簿
        sheet = wookbook.getSheetAt(0);
       // Mapmaplist = getPictures1((HSSFSheet) sheet);
    }
    catch (Exception ex)
    {
        try
        {     //2007版本的excel，用.xlsx结尾
            wookbook = new XSSFWorkbook(toFile.getInputStream());//得到工作簿
            sheet = wookbook.getSheetAt(0);
            Mapmaplist = getPictures2((XSSFSheet) sheet);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    String space="";
    List<Product> productlist = new ArrayList<>();
    boolean firstrow = true;
    //获得所有数据
    for (Row row : sheet) {
        //跳过第一行
        if (firstrow) {
            firstrow = false;
            continue;}
        //空间位置(为空则停止解析)
        Cell cell = row.getCell(0);
        if (cell == null){
            break;
        }
        cell.setCellType(CellType.STRING);
        space =cell.getStringCellValue().toString();
        if (StringUtils.isBlank(space)){
            break;
        }
        DataFormatter formatter = new DataFormatter();//excel单元格接收器
        Product product = new Product();
        product.setP_id(Integer.parseInt(formatter.formatCellValue(row.getCell(0))));
        product.setPname(row.getCell(1).getStringCellValue());
        product.setPrice(row.getCell(2).getNumericCellValue());
        product.setUnit(row.getCell(3).getStringCellValue());
        product.setStock(Integer.parseInt(formatter.formatCellValue(row.getCell(4))));
        product.setCategory_id(Integer.parseInt(formatter.formatCellValue(row.getCell(5))));
        productlist.add(product);
   }
   mapper.saveAllProduct(productlist);
    try {

        int b=0;//截取字符“-”的下标
       int  i=0;//循环控制变量
        List<String> orderlist=new ArrayList<String>();

        for(Map.Entry<Map<String, PictureData>,Integer> entrySet:Mapmaplist.entrySet()){
            Product_image img = new Product_image();
            maplist=entrySet.getKey();
            prinPath=printImg(maplist);
            orderlist.addAll(maplist.keySet());
            b=orderlist.get(i).indexOf("-");
            img.setProduct_id(Integer.parseInt(orderlist.get(i).substring(0,b)));
            img.setUrl("/static/image/"+prinPath.get(i));
            img.setSequence(Integer.parseInt(orderlist.get(i).substring(b+1,orderlist.get(i).length())));
            imagelist.add(img);
            i++;
        }
       mapper.saveAllProduct_img(imagelist);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    /**
     * 获取图片和位置 (xls)
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<Map<String, PictureData>,Integer> getPictures1 (HSSFSheet sheet) throws IOException {
        Map<Map<String, PictureData>,Integer> mapMap=new LinkedHashMap();
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        DataFormatter formatter = new DataFormatter();//excel单元格接收器
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                //获得商品id
                Row row=sheet.getRow(cAnchor.getRow1());
                int id=Integer.parseInt(formatter.formatCellValue(row.getCell(0)));
                String key = id + "-" + cAnchor.getCol1();
                map.put(key,pdata);
                mapMap.put(map,id);
            }
        }
        return mapMap;
    }
    /**
     * 获取图片和位置 (xlsx)
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<Map<String, PictureData>,Integer> getPictures2 (XSSFSheet sheet) throws IOException {
        Map<Map<String, PictureData>,Integer> mapMap=new LinkedHashMap();
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        DataFormatter formatter = new DataFormatter();//excel单元格接收器
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                  //获得商品id
                    Row row=sheet.getRow(marker.getRow());
                    int id=Integer.parseInt(formatter.formatCellValue(row.getCell(0)));
                    String key = id + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                    mapMap.put(map,id);

                }
            }
        }
        return mapMap;
    }
    //图片写出
    public List<String> printImg(Map<String, PictureData> sheetList) throws Exception {
        List<String> filePathList=new ArrayList<String>();
        Object key[] = sheetList.keySet().toArray();
        String filePath = "";
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引
            String picName = key[i].toString();
            // 获取图片格式
            String ext = pic.suggestFileExtension();
            byte[] data = pic.getData();
            //文件上传七牛
//            QiNiuUtils.uploadOneObject(data,"111_"+picName + "." + ext);
            //图片保存路径
            filePath = "D:\\Java\\ldea\\ShoppingCenter\\src\\main\\webapp\\static\\image\\pro" + picName + "." + ext;
            FileOutputStream out = new FileOutputStream(filePath);
            filePathList.add("pro" + picName + "." + ext);
            out.write(data);
            out.close();
            }
        return filePathList;
        }


        //查询单个商品
    @Override
    public List<ProductDetails> findOneProduct(int product_id) {
        return mapper.findOneProduct(product_id);
    }


    //查询分类商品
    @Override
    public List<ProductDetails> findAllProduct(int categoryId) {
        return mapper.findAllProduct(categoryId);
    }

    //查询分类商品
    @Override
    public List<ProductDetails> findMrtjList() {
        return mapper.findMrtjList();
    }

    @Override
    public List<ProductDetails> searchProduct(String store) {
        return mapper.searchProduct(store);
    }

    @Override
    public int getProductCount() {
        return mapper.getProductCount();
    }

    @Override
    public List<ProductDetails> findAllProduct(Integer offset, Integer limit) {
        return mapper.selectAllProduct(offset,limit);
    }


    public int deletePById(Integer pId){
        return mapper.deleteOneById(pId);
    }
}

