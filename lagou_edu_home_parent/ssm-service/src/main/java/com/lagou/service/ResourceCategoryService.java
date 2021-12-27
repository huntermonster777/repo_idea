package com.lagou.service;

import java.util.List;

public interface ResourceCategoryService {
    /**
     * 查询所有资源分类
     */
    public List<com.lagou.domain.ResourceCategory> findAllResourceCategory();
}
