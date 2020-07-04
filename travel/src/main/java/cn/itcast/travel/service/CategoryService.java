package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 从数据库中加载所有旅游分类
     * @return
     */
    List<Category> findAll();
}
