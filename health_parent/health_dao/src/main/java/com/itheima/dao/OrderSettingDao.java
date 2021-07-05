package com.itheima.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    //添加预约信息
    int add(OrderSetting orderSetting);
    //更新预约信息
    int update(OrderSetting orderSetting);
    //查询已存在预约信息的时间
    OrderSetting findOrderSettingByDate(Date date);
    //查询从某一个时间到另一个时间段的预约信息
    List<OrderSetting> findOrderByMonth(@Param("begin") String begin,@Param("end") String end);
    /**
     * 根据日期修改预约数量
     * @param date 日期
     * @param number 预约数
     * @return
     */
    int updateOrderNumber(@Param("date") String date, @Param("number") int number);
}
