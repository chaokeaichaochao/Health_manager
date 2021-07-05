package com.itheima.Controller;

import com.itheima.constant.MessageConstant;
import com.itheima.health.pojo.*;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {


    @Autowired
    private CheckItemService checkItemService;


    /**
     * 添加检查项
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        System.out.println(checkItem.getSex());
        System.out.println(checkItem.getName());
        //调用service
        int row = checkItemService.add(checkItem);
        //判断结果
        Result result = null;
        if (row > 0) {
            //添加成功
            result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

        } else {
            result = new Result(true, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return result;
    }

    /**
     * 分页查询当前页,每页个数,查询条件
     * {}
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {


        Result result = null;
        try {
            //调用service
            PageResult<CheckItem> pageResult = checkItemService.findPages(queryPageBean);
            //组装返回
            result = new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        System.out.println(result);

        return result;
    }
    /**
     * 删除检查项
     */
    @RequestMapping("/delete")
    public Result delete(Integer checkItemId) {
        System.out.println(checkItemId);

        //1调用service
        int i = checkItemService.delete(checkItemId);

        Result result = null;
        //如果无关联项再删除


        if (i > 0) {
            result = new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } else {

            result = new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return result;
    }

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {


        //1调用service
        int i = checkItemService.update(checkItem);

        Result result = null;
        //如果无关联项再删除


        if (i > 0) {
            result = new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } else {

            result = new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return result;
    }

    /**
     * 查询所有 检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        Result result = null;
        try {
            List<CheckItem>list = checkItemService.findAll();
            result =new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            result =new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }


}
