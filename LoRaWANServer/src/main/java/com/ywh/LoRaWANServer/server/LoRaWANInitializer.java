package com.ywh.LoRaWANServer.server;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ywh.LoRaWANServer.enums.LoRaWANClassEnum;
import com.ywh.LoRaWANServer.handler.FRMPayloadHandler;
import com.ywh.LoRaWANServer.handler.MACPayloadHandler;
import com.ywh.LoRaWANServer.handler.PHYPayloadHandler;
import com.ywh.LoRaWANServer.temp.domain.DownInfoForm;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 服务初始化类，添加协议拆包和封包对象和初始化数据.
 */
@Component
public class LoRaWANInitializer extends ChannelInitializer<NioDatagramChannel> {

  /**
   * 默认为ClassC
   */
  public static LoRaWANClassEnum classMod = LoRaWANClassEnum.ClassC;

  public static LinkedBlockingQueue<DownInfoForm> queueDown = new LinkedBlockingQueue<DownInfoForm>();
  public static byte[] beacon_time = null;
  public static int pingNb = 4; //ping slot数量,必须为2^k幂次方，0<=k<=7
  public static int ping_period = (int) (Math.pow(2, 12) / pingNb);
  public static int beacon_reserved = 2120;//2.12s=2120ms
  public static float beacon_guard = (float) 3.000;
  //beacon_window=becon_period - beacon_reserved -beacon_guard
  public static int pingOffset = 0;
  public static int periodicity = 32;
  public static int Delay = 0;
  public static int slotLen = 30;   //一个ping slot时长，30ms

  @Value("${lora.client.port:2019}")
  public static int ClientPort;
  
  @Override
  protected void initChannel(NioDatagramChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast("decoder", new LoRaWANDecoder())
            .addLast("PhyDataHandler", new PHYPayloadHandler())
            .addLast("MacDataHandler", new MACPayloadHandler())
            .addLast("FrmDataHandler", new FRMPayloadHandler());
  }

}
