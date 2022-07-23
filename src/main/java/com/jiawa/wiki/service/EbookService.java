package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Ebook;
import com.jiawa.wiki.domain.EbookExample;
import com.jiawa.wiki.mapper.EbookMapper;
import com.jiawa.wiki.req.EbookQueryReq;
import com.jiawa.wiki.req.EbookSaveReq;
import com.jiawa.wiki.resp.EbookQueryResp;
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
public class EbookService {

    private static final Logger LOG= LoggerFactory.getLogger(EbookService.class);

    @Autowired
    private EbookMapper ebookMapper;

    @Autowired
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
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

        List<EbookQueryResp> ebookQueryRespList = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp=new PageResp<>();
        pageResp.setList(ebookQueryRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    //既要支持新增也要支持更新
    public void save(EbookSaveReq ebookQueryReq) {
        Ebook ebook=CopyUtil.copy(ebookQueryReq,Ebook.class);
        if(ObjectUtils.isEmpty(ebookQueryReq.getId())){
            //新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        }
        else{
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
