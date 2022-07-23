package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Category;
import com.jiawa.wiki.domain.CategoryExample;
import com.jiawa.wiki.mapper.CategoryMapper;
import com.jiawa.wiki.req.CategoryQueryReq;
import com.jiawa.wiki.req.CategorySaveReq;
import com.jiawa.wiki.resp.CategoryQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.util.CopyUtil;
import com.jiawa.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by echo on 2022/7/20 17:00
 */
@Service
public class CategoryService {

    private static final Logger LOG= LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        /**
         * 只对第一个SQL语句有效
         */
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo=new PageInfo<>(categoryList);
        LOG.info("总行数："+pageInfo.getTotal());
        LOG.info("总页数："+pageInfo.getPages());

        List<CategoryQueryResp> categoryQueryRespList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp=new PageResp<>();
        pageResp.setList(categoryQueryRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryQueryResp> categoryQueryRespList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return categoryQueryRespList;
    }

    //既要支持新增也要支持更新
    public void save(CategorySaveReq categoryQueryReq) {
        Category category=CopyUtil.copy(categoryQueryReq,Category.class);
        if(ObjectUtils.isEmpty(categoryQueryReq.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }
        else{
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
