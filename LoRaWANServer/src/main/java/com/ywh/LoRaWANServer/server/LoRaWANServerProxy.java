package com.ywh.LoRaWANServer.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 服务端代理类
 * LoRaWAN协议解析源码来源： https://github.com/loveLynch/LoRaServer_Netty
 * 参考的文档有： 《LoraWAN协议说明书-中文》
 * 参考的网址有： https://blog.csdn.net/huyuan7494/article/details/79042503
 * 
 */
@Component
public class LoRaWANServerProxy {
  private static final Logger logger = LoggerFactory.getLogger(LoRaWANServerProxy.class);

  @Value("${lora.server.port:2019}")
  int serverPort = 2019;

  private Channel channel;
  EventLoopGroup eventLoopGroup;

  @PostConstruct
  public void start() throws InterruptedException {
    // 配置NIO线程组
    eventLoopGroup = new NioEventLoopGroup();
    // 服务器辅助启动类配置
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.channel(NioDatagramChannel.class)
            .group(eventLoopGroup)
//            .option(ChannelOption.SO_BROADCAST, true)
            .handler(new LoRaWANInitializer());

    try {
      channel = bootstrap.bind(serverPort).sync().channel();
      logger.info("LoRaWAN协议设备接入网关开始监听UDP端口： " + serverPort);
//      channel.closeFuture().sync();
      channel.closeFuture().await();
//      channel.closeFuture().syncUninterruptibly();
    } finally {
      eventLoopGroup.shutdownGracefully();
    }
    
  }

  
  /**
   * 停止服务
   */
  @PreDestroy
  public void stop() {
    logger.info("LoRaWAN协议正在停止，销毁资源...");
    if (channel != null) {
      channel.closeFuture().syncUninterruptibly();
      channel = null;
    }
    if (eventLoopGroup != null) {
      eventLoopGroup.shutdownGracefully();
      eventLoopGroup = null;
    }
  }

}
