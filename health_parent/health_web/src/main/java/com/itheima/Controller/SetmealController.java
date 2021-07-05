package com.itheima.Controller;

import com.itheima.constant.MessageConstant;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            //1.准备文件名字
            String oldNmae = imgFile.getOriginalFilename();
            String newName= UUID.randomUUID().toString()+oldNmae.substring(oldNmae.lastIndexOf('.'));
            //2.把这个文件上传到七牛云
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),newName);
            /**
             *  3.返回结果给也页面
             *        3.1返回图片地址供页面显示
             *        3.2点击确认后,完成新建套餐操作,也需要吧图片名字保存到数据库,以及返回页面
             *        3.3 在这里不能盲目的直接把图片的地址返回。现在打算拆分返回。
             *                     3.4 使用一个map集合来封装返回的数据： 包含两个数据
             *                         3.4.1 一个是七牛云，自己的空间的域名
             *                         3.4.2 一个是图片的名字
             */
            Map<String ,String>imglist=new HashMap<>();
            imglist.put("domain",QiNiuUtils.DOMAIN);
            imglist.put("imgName",newName);
            return  new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imglist);
        } catch (IOException e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    @PostMapping ("/addsetmeal")
    public Result addsetmeal(int checkgroupIds[],@RequestBody  Setmeal setmeal  ){
       // System.out.println(checkgroupIds);
        //System.out.println(setmeal.getAge());
        //调用service工作
        int i = setmealService.add(checkgroupIds, setmeal);
        Result result=null;
        if (i>0){
            result=new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }else{
            result=new Result(true,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return result;
    }
}
