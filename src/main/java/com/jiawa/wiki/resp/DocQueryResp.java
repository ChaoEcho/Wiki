package com.jiawa.wiki.resp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DocQueryResp {
    private Long id;

    private Long ebookId;

    private Long parent;

    private String name;

    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;

}