package com.jiawa.wiki.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DocQueryReq extends PageReq{
    private Long ebookId;
}