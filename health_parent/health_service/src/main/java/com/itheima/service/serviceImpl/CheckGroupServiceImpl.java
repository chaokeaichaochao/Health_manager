package com.itheima.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.dao.CheckGroupDao;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.PageResult;
import com.itheima.health.pojo.QueryPageBean;
import com.itheima.service.CheckGroupService;
import com.sun.tools.javac.comp.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    /**
     * 新增检查组
     *      1. 检查组包含两份数据，一份是自己的检查组的基本信息，一份是检查组使用了哪些检查项
     *      2. 这两份数据需要存到两张不同的表： t_checkgroup & t_checkgroup_checkitem
     *          2.1 先把基本的信息存入到检查组的表里面 ：t_checkgroup
     *          2.2 再把这个检查组用到了哪些检查项，把这些记录保存到中间表 ：  t_checkgroup_checkitem
     *      3. 一定要先往t_checkgroup这张表添加数据，这样子我们才能得到主键的返回，才能知道这个检查组的
     *          id值是多少。有了id值，才能去往中间表里面添加记录。
     *
     * @param checkGroup  检查组的基本信息
     * @param checkitemIds 检查组包含的检查项的id值
     * @return
     */

    @Override
    public int add(CheckGroup checkGroup, int[] checkitemIds) {


        int row = checkGroupDao.add(checkGroup);
        /*
            2. 往t_checkgroup_checkitem添加检查项信息
                2.1 由于从页面过来的时候，这个检查组可能选择了很多的检查项，
                    所以这里要遍历出来每一个检查项
                2.2 遍历一次，就往中间表里面添加一条记录。
                    checkitemIds = 【28,29,30】;
         */
        int row1=0;
        if (row>0){
            for (int checkitemId : checkitemIds) {
              row1+=checkGroupDao.addItem(checkGroup.getId(),checkitemId);
            }
        }
        return (row>0&&row1==checkitemIds.length)?1:0;
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //判断是否有条件查询
        if(!StringUtil.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //使用PageHelper.startPage(页码，大小)
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //调用dao层
        Page<CheckGroup>page=checkGroupDao.findByCondition(queryPageBean.getQueryString());
        //获取分页结果
        //封装到pageResult 返回
        PageResult<CheckGroup> pageResult=new PageResult<>(page.getTotal(),page.getResult());

        return pageResult;
    }



    //通过id查询检查组信息
    @Override
    public CheckGroup findById(int id) {

        return checkGroupDao.findById(id);
    }
    //通过id查询选项中的检查项id集合
    @Override
    public List<Integer> findCheckGroupId(int id) {
        return checkGroupDao.findCheckGroupId(id);
    }
    //更新检查组
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.更新检查组信息
        checkGroupDao.update(checkGroup);
        //2.获取新增的检查组id
        Integer checkGroupId=checkGroup.getId();
        //3.先通过检查组id删除检查组和检查项的旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroupId);
        //4.遍历选项中的id数组,空判断
        if(null!=checkitemIds){
            //5.添加组关系
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addItem(checkGroupId,checkitemId);
            }
        }

    }

    /**
     * 删除分组信息必须需要排除是否在套餐表中使用
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int delete(int id) {

        //调用Dao查询此id检查组下是否有检查项
       int row= checkGroupDao.findSetmeal(id);
       if(row==0){
           int deleteItem_group=checkGroupDao.deleteItem_group(id);
           int delete=checkGroupDao.deleteCheckGroup(id);
           if (delete>0&&deleteItem_group>0){
               return 1;
           }
       }
        return 0;
    }

    @Override
    public List<CheckGroup> findAll() {
        //调用Dao层查询分组信息

        return checkGroupDao.findAll();
    }


}
