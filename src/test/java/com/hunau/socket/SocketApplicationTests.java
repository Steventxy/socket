package com.hunau.socket;

import com.hunau.domain.Data;
import com.hunau.service.DataService;
import com.hunau.service.Impl.DataServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocketApplicationTests {

    @Autowired
    DataServiceImpl dataService;

    @Test
    void contextLoads() {
        System.out.println(dataService);
//        dataService.collData(new Data(30.23,20.71));
    }

}
