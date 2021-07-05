package com.itheima.dao;

import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {


    //添加套餐信息,获取id
    int addSetmeal( Setmeal setmeal);
    //添加关联表信息
    int addSetmeal_checkgrouop(@Param("setmeal_id") int setmeal_id, @Param("checkgroupId") int checkgroupId);
    //查询数据所有图片信息
    List<String> getImgs();
}



