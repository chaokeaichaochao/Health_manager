package com.itheima.Controller;


import com.itheima.constant.MessageConstant;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.pojo.Result;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 导入预约信息,导入excel文件
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            /**
             * 1.读取excel文件内容
             *      1.1表格里面可能是多行,返回list集合
             *      1.2里面包装每一行数据,用数组包装
             *      1.3数组的第0位,表示第一个格子内容,依次...
             */
            List<String[]> stringList = POIUtils.readExcel(excelFile);
            //遍历集合,取出来预约的日期以及预约的最大数量,封装到OrderSetting
            List<OrderSetting>orderSettingList=new ArrayList<>();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd ");
            //3.组装数据封装到OrderSetting
            for (String[] strings : stringList) {
                OrderSetting os = new OrderSetting(sf.parse(strings[0]),Integer.parseInt(strings[1]));
                orderSettingList.add(os);
            }
            //4.交代service干活
            int row =orderSettingService.add(orderSettingList);
            Result result=null;
            if(row>0){
                result=new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            }else{
                result=new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
            }
            return result;


        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false ,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false , MessageConstant.IMPORT_ORDERSETTING_FAIL);

    }

    /**
     * 日历展示预约设置信息
     * @param date
     * @return
     */
    @RequestMapping("/findorderbymonth")
    public Result findOrderByMonth(String date){
        //调用service
        List<OrderSetting>list=orderSettingService.findOrderByMonth(date);
        //页面已写好,需要的数据和service拿到数据不一样,
        //封装
        List<Map> mapList=new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            //每一条预约对象是一个map,之所以要封装是因为页面要求的属性的名字和OrderSetting属性名不一样
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());

            mapList.add(map);
        }
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,mapList);
    }

    @RequestMapping("updateordernumber")
    public Result updateOrderNumber(String date,int number){
        //1.调用service干活
        int row = orderSettingService.updateordernumber(date,number);
        //2.判断
        Result result = null;
        if(row >0 ){
            result = new Result(true , "设置预约数量成功！");
        }else{
            result = new Result(false , "设置预约数量失败！");
        }
        return result;
    }
}
