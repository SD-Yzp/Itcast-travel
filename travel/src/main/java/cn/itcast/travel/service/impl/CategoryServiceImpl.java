package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //从redis中查询是否存在
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        //创建list接收结果
        List<Category> list = new ArrayList<Category>();
        if(category == null || category.size() == 0){
            //redis中不存在
            list = categoryDao.findAll();
            //存储到redis中
            for (Category c : list) {
                jedis.zadd("category",c.getCid(),c.getCname());
            }
        }else{
            //redis中存在
            for (Tuple tuple : category) {
                Category c = new Category();
                c.setCid((int) tuple.getScore());
                c.setCname(tuple.getElement());
                //向list中添加
                list.add(c);
            }
        }

        return list;
    }
}
