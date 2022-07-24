package com.jiawa.wiki.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class DocSaveReq {
    private Long id;

    @NotNull(message = "【ebookId】不能为空")
    private Long ebookId;

    @NotNull(message = "【parent】不能为空")
    private Long parent;

    @NotNull(message = "【name】不能为空")
    private String name;

    @NotNull(message = "【sort】不能为空")
    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;

}