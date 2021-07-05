package com.itheima.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {
    /**
     * 添加预约信息
     * @param list
     * @return
     */
    int add(List<OrderSetting> list);

    /**
     * 查询指定月份日期
     * @param date
     * @return
     */
    List<OrderSetting> findOrderByMonth(String date);
    /**
     * 根据日期，修改预约数量
     * @param date 日期
     * @param number 数量
     * @return
     */
    int updateordernumber(String date, int number);
}
