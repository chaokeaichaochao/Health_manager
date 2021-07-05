package com.itheima.service.serviceImpl;

import com.itheima.dao.SetmealDao;
import com.itheima.health.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Override
    public int add(int[] checkgroupIds, Setmeal setmeal) {
        //添加套餐信息,同时应该在关联表中遍历添加检查组信息,且要获取添加后的id
        int row=setmealDao.addSetmeal(setmeal);
        //获取添加后的套餐id,
        Integer setmeal_id = setmeal.getId();
        //遍历添加套餐和检查组关系
        int row1=0;
        for (int checkgroupId : checkgroupIds) {
            row1+=setmealDao.addSetmeal_checkgrouop(setmeal_id,checkgroupId);
        }

        if(row>0&&row1==checkgroupIds.length){
            return 1;
        }
        return 0;
    }
}
