package com.hunau.service.Impl;

import com.hunau.domain.Data;
import com.hunau.mapper.DataMapper;
import com.hunau.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TanXY
 * @create 2020/6/20 - 21:36
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    DataMapper dataMapper;

    @Override
    public void collData(Data data) {
        dataMapper.collData(data);
    }
}
