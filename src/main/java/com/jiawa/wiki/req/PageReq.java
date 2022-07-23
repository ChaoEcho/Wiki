package com.jiawa.wiki.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class PageReq {

    @NotNull(message = "[页码]不能为空")
    @Min(value = 1,message = "[页码]不能低于1")
    private Integer page;

    @NotNull(message = "[每页条数]不能为空")
    @Max(value = 1000,message = "[每页条数]不能超过1000")
    private Integer size;
}