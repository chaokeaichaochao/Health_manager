package com.itheima.service;

import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.PageResult;
import com.itheima.health.pojo.QueryPageBean;

import java.util.List;

public interface CheckItemService {
    //添加检查项
    int add(CheckItem checkItem);
    //页面使用elementUI,必须返回total,list, PageResult包含
    PageResult<CheckItem> findPages(QueryPageBean queryPageBean);


    //删除检查项
    int delete(int id);

    int update(CheckItem checkItem);

    List<CheckItem> findAll();
}

