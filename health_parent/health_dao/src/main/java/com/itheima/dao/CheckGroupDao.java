package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {


     Page<CheckGroup> findByCondition(String queryString) ;

    int add(CheckGroup checkGroup);

    int addItem(@Param("checkGroupId") int checkGroupId , @Param("checkItemId") int checkItemId);

    //通过id查询检查组信息
    CheckGroup findById(int id);
    //通过检查组id查询选项中检查项的集合
    List<Integer>findCheckGroupId(int id);

    //根据检查组id删除检查组与检查项的旧关系
    void deleteCheckGroupCheckItem(Integer checkGroupId);
    //更新信息
    void update(CheckGroup checkGroup);


    int findSetmeal(int id);

    int deleteCheckGroup(int id);

    int deleteItem_group(int id);

    List<CheckGroup> findAll();

}
