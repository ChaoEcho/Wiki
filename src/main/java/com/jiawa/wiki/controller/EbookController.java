package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.EbookReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.EbookResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.service.EbookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by echo on 2022/7/20 21:42
 */
@RestController
@RequestMapping("/ebook")
@Api(value = "电子书管理接口")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp<PageResp<EbookResp>> list(EbookReq ebookReq){
        CommonResp<PageResp<EbookResp>> resp=new CommonResp<>();
        PageResp<EbookResp> pageResp = ebookService.list(ebookReq);
        resp.setContent(pageResp);
        return resp;
    }
}
