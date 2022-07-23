package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.CategoryQueryReq;
import com.jiawa.wiki.req.CategorySaveReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.CategoryQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by echo on 2022/7/20 21:42
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "分页查询分类")
    @GetMapping("/list")
    public CommonResp<PageResp<CategoryQueryResp>> list(@Valid CategoryQueryReq categoryQueryReq) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> pageResp = categoryService.list(categoryQueryReq);
        resp.setContent(pageResp);
        return resp;
    }

    @ApiOperation(value = "查询所有分类")
    @GetMapping("/all")
    public CommonResp<List<CategoryQueryResp>> all() {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> all = categoryService.all();
        resp.setContent(all);
        return resp;
    }

    @ApiOperation(value = "保存分类更新信息")
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq categoryQueryReq) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(categoryQueryReq);
        return resp;
    }

    @ApiOperation(value = "删除分类")
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id) {
        CommonResp resp = new CommonResp<>();
        if (!categoryService.delete(Long.parseLong(id))) {
            resp.setSuccess(false);
            resp.setMessage("父分类中还含有子分类！");
        }
        return resp;
    }
}
