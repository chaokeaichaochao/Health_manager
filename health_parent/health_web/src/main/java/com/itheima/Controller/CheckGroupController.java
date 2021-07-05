package com.itheima.Controller;

import com.itheima.constant.MessageConstant;
import com.itheima.health.pojo.*;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Autowired
    private CheckGroupService checkGroupService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,int[] checkitemIds){


        int row=checkGroupService.add(checkGroup,checkitemIds);
        System.out.println(row);
        Result result=null;
        if(row>0){
            result=new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }else{
            result=new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        return result;
    }
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody  QueryPageBean queryPageBean){
        Result result=null;
        try {
            //调用业务
            PageResult<CheckGroup> pageResult=checkGroupService.findPage(queryPageBean);
            //封装result返回
            result= new Result(true ,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            result= new Result(false ,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
            return result;
    }


    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){

        Result result = null;
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);

         result =new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            result =new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
    @GetMapping("/findCheckGroupId")
    public Result findCheckGroupId(int id){
        Result result = null;
        try {
            List<Integer> list = checkGroupService.findCheckGroupId( id);

            result =new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            result =new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }

    @PostMapping("/update")
    public Result findCheckGroupId(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){

          checkGroupService.update(checkGroup,checkitemIds);

        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/deleteCheckGroupId")
    public Result deleteCheckGroupId(int id){
        Result result= null;
        try {
            int row=checkGroupService.delete(id);
            result = new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"删除失败");
        }
        return result;
    }
    @GetMapping("/findAll")
    public Result fndAll(){
        Result result=null;
        try {
            //查询所有分组信息,返回数据
            List<CheckGroup>groupList=checkGroupService.findAll();
            //封装结果
            result = new Result(true,"查询分组信息成功",groupList);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false,"查询分组信息失败");
        }

        return  result;
    }
}
