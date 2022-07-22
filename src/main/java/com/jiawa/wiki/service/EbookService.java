package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Ebook;
import com.jiawa.wiki.domain.EbookExample;
import com.jiawa.wiki.mapper.EbookMapper;
import com.jiawa.wiki.req.EbookReq;
import com.jiawa.wiki.resp.EbookResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.util.CopyUtil;
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
public class EbookService {
    
    private static final Logger LOG= LoggerFactory.getLogger(EbookService.class);

    @Autowired
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();

        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }

        /**
         * 只对第一个SQL语句有效
         */
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo=new PageInfo<>(ebookList);
        LOG.info("总行数："+pageInfo.getTotal());
        LOG.info("总页数："+pageInfo.getPages());

        List<EbookResp> ebookRespList = CopyUtil.copyList(ebookList, EbookResp.class);
        PageResp<EbookResp> pageResp=new PageResp<>();
        pageResp.setList(ebookRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }
}
