package com.itheima.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.PageResult;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    @Override
    public int add(CheckItem checkItem) {
        return checkItemDao.add(checkItem);
    }
    /**
     * 新增检查项
     * service一般调用dao查询数据库,无法返回特殊类
     * 补充数据对象
     * @param
     * @return
     */
    @Override
    public PageResult<CheckItem> findPages(QueryPageBean queryPageBean) {
        //设置每页查询第几页,查询多少条
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //1.调用dao查询总记录数,当前页集合数据

        Page<CheckItem> page = checkItemDao.findPage(queryPageBean);
        //2.准备数据
        long total=page.getTotal();
        List<CheckItem>rows = page.getResult();

        //3.创建PageResult


        return new PageResult<>(total,rows);
    }



    @Override
    public int delete(int checkItemId) {
        long countById = checkItemDao.findCountById(checkItemId);
        if (countById>0){
            System.out.println("禁止删除,存在检查组使用"+countById);
           return 0;
        }
        return checkItemDao.delete(checkItemId);
    }

    @Override
    public int update(CheckItem checkItem) {

        return checkItemDao.update(checkItem);
    }
//查询检查组所有
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


}
