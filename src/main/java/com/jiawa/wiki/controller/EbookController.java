package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.EbookQueryReq;
import com.jiawa.wiki.req.EbookSaveReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.EbookQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.service.EbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by echo on 2022/7/20 21:42
 */
@RestController
@RequestMapping("/ebook")
@Api(tags = "电子书管理接口")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @ApiOperation(value = "查询电子书")
    @GetMapping("/list")
    public CommonResp<PageResp<EbookQueryResp>> list(@Valid EbookQueryReq ebookQueryReq){
        CommonResp<PageResp<EbookQueryResp>> resp=new CommonResp<>();
        PageResp<EbookQueryResp> pageResp = ebookService.list(ebookQueryReq);
        resp.setContent(pageResp);
        return resp;
    }

    @ApiOperation(value = "保存更新信息")
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq ebookQueryReq){
        CommonResp resp=new CommonResp<>();
        ebookService.save(ebookQueryReq);
        return resp;
    }

    @ApiOperation(value = "删除电子书")
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable String id){
        CommonResp resp=new CommonResp<>();
        ebookService.delete(Long.parseLong(id));
        return resp;
    }
}
