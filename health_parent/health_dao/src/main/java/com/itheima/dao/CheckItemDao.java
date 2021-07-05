package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.QueryPageBean;

import java.util.List;

public interface CheckItemDao {
    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    int add(CheckItem checkItem);

    /**
     * 查询,
     * @param queryPageBean
     * @return
     */
    Page<CheckItem> findPage (QueryPageBean queryPageBean);
    /**
     * 查询检查组的表,看此检查项是否被检查组选用
     */
    long findCountById(int checkItem);
    /**
     * 删除方法
     * @param id
     * @return
     */
    int delete(Integer id);

    int update(CheckItem checkItem);

    List<CheckItem> findAll();
}
