package com.hunau;

import com.hunau.socket.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocketApplication {

    public static void main(String[] args) {

        SpringApplication.run(SocketApplication.class, args);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new NettyClient("192.168.4.1", 5000).startConn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
