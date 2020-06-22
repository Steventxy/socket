package com.hunau.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author TanXY
 * @create 2020/6/20 - 21:31
 */
public class NettyClient {

    private String IP;

    private int port;

    private static Channel channel = null;

    public NettyClient(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    public NettyClient() {
    }

    public void startConn() throws Exception {
        // 创建一个新的 EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建一个 启动类 实例
            Bootstrap b = new Bootstrap();
            // 注册线程池
            b.group(group)
                    // 使用NioSocketChannel来作为连接用的channel类 ， TCP Socket
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 绑定连接端口和host信息
                    .remoteAddress(new InetSocketAddress(port))
                    // 绑定连接初始化器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        // 注册通道
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new Handler());
                        }
                    });
            // 异步连接服务器
            ChannelFuture cf = b.connect(IP,port).sync();
            channel = cf.channel();

            // 异步等待关闭连接channel
            cf.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
        }
    }

}
