package com.jiawa.wiki.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageReq {
    private Integer page;

    private Integer size;
}