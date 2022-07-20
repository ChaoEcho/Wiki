package com.jiawa.wiki.mapper;

import com.jiawa.wiki.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by echo on 2022/7/20 16:42
 */
@Mapper
public interface TestMapper {

    List<Test> list();

}
