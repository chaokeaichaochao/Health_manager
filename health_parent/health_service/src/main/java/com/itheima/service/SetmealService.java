package com.itheima.service;

import com.itheima.health.pojo.Setmeal;
import org.springframework.web.bind.annotation.RequestBody;

public interface SetmealService {

    //添加套餐的方法
    int add(int checkgroupIds[],@RequestBody Setmeal setmeal );
}
