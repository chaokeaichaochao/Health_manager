package com.itheima.service;

import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.PageResult;
import com.itheima.health.pojo.QueryPageBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckGroupService {

    //新增检查组
    int add(CheckGroup checkGroup , int [] checkitemIds);
    //分页查询
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    //通过id查询检查项集合
    CheckGroup findById(int id);
    //通过检查组id查询选中的检查项id集合
    List<Integer> findCheckGroupId(int id);
    //更新检查组
     void update(CheckGroup checkGroup, Integer[] checkitemIds);



    //删除检查组信息,需要排除时否在套餐表中使用
    int delete(int id);
    //查询所有分组信息
    List<CheckGroup> findAll();
}
