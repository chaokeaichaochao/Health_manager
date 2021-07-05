package com.itheima.service.serviceImpl;

import com.itheima.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * 批量导入预约信息
     * @param list  导入的预约信息集合
     * @return
     */
    @Override
    public int add(List<OrderSetting> list) {
        int row1=0;
        int row2=0;
        //1.遍历集合,取出来每一个预约对象,预约对象包含预约的日期和最大可以预约数
        for (OrderSetting orderSetting : list) {
            //得到预约的日期和最大预约数
            Date date = orderSetting.getOrderDate();
            int number = orderSetting.getNumber();
             /*
                3. 往数据库里面添加|更新数据
                    3.1 不能盲目的认为批量导入就是直接往数据库里面添加预约的数据即可
                    3.2 要考虑到数据库里面已经存在了现在正要导入的预约的日期这种情况
                    3.3 要先拿着现在要导入的这个日期去数据库里面查询记录，数据库里面有没有这一天的预约数据
                        如果有：
                            3.3.1 数据库里面有现在要导入的这一天的预约记录，要更新这条记录
                            3.3.2 要比较数库库里面的已经预约的数量（reservations） 和 现在要到如的可预约的数量大小关系
                                如果 已预约的数量 >  现在要导入的可预约的数量，禁止更新
                                否则，就可以更新了！
                         如果没有：
                            直接添加这条记录即可。
                     3.4 到底要做的是更新还是添加，取决于数据库里面有没有现在要导入这一天的预约记录
             */

            //3. 根据日期去查询数据库里面的记录信息
            OrderSetting orderData=orderSettingDao.findOrderSettingByDate(date);
            if(orderSetting!=null){//表名有记录
                //判断大小关系
                 if (number<orderData.getReservations()){//禁止导入
                     throw new RuntimeException("可预约数量必须要大于已经预约的数量！");

                 }else{//作更新
                     row1+=orderSettingDao.update(orderSetting);

                 }

            }else{//没有记录
                row2+=orderSettingDao.add(orderSetting);

            }
        }
        return (row1+row2==list.size())?1:0;
    }

    /**
     * 查询月份信息
     * @param date
     * @return
     */
    @Override
    public List<OrderSetting> findOrderByMonth(String date) {
        //1拼接开始日期和结束日期
        String begin=date+"-1";
        String end = date+"-1";
        return orderSettingDao.findOrderByMonth(begin,end);
    }
    /**
     * 根据日期，修改预约数量
     * @param date 日期
     * @param number 数量
     * @return
     */
    @Override
    public int updateordernumber(String date, int number) {


        return orderSettingDao.updateOrderNumber(date , number);
    }
}
