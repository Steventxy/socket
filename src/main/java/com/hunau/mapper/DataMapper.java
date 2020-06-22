package com.hunau.mapper;

import com.hunau.domain.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author TanXY
 * @create 2020/6/20 - 21:36
 */
@Mapper
@Repository
public interface DataMapper {
    public void collData(Data data);
}
