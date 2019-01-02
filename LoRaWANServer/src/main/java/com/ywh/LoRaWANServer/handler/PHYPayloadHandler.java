/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.ywh.LoRaWANServer.handler;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ywh.LoRaWANServer.enums.MTypeEnum;
import com.ywh.LoRaWANServer.packet.PHYLayer;
import com.ywh.LoRaWANServer.packet.PHYPayload;
import com.ywh.LoRaWANServer.server.LoRaWANInitializer;
import com.ywh.LoRaWANServer.temp.domain.DownInfoForm;
import com.ywh.LoRaWANServer.util.JsonUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


/**
 * TCP服务处理类.
 * 
 */
@Component
public class PHYPayloadHandler extends  ChannelInboundHandlerAdapter {
  private static final Logger logger = LoggerFactory.getLogger(PHYPayloadHandler.class);
  
  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    logger.info("通道已注册", ctx.channel().remoteAddress());
    super.channelRegistered(ctx);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg == null) {
      logger.error("msg is null");
      return;
    }

    PHYLayer phyLayer = (PHYLayer)msg;
    PHYPayload phyPayload = phyLayer.getPhyPayload();
    if (phyPayload.getMacPayload() != null) {
      byte mtype = phyPayload.getMhdr().getMtype();
      MTypeEnum mtypeEnum = MTypeEnum.getMTypeEnum(mtype);
      switch (mtypeEnum) {
        case JoinRequest:
          break;
        case JoinAccept:
          break;
        case UnconfirmedDataUp:
        case UnconfirmedDataDown:
        case ConfirmedDataUp:
        case ConfirmedDataDown:
          break;
        case RFU:
          logger.info("recived mtype: RFU");
          break;
        case Proprietary:
          logger.info("received mtype: Proprietary");
          break;
        default:
          logger.error("cannt parse mtype: {}", mtype);
          break;
      }
      /*
      byte mtype = datagramPacket.content().getByte(3);
      switch (mtype) {
        case PKT_PUSH_DATA:
          handlePKTPushData(ctx, datagramPacket, list);
          break;
        case PKT_PULL_DATA:
          handlePKTPullData(ctx, datagramPacket, list);
          break;
        case PKT_PULL_ACK:
          break;
        default:
          logger.error("unexpected command, data is : {}",
              ByteBufUtil.hexDump(datagramPacket.content()));
      }
      */
    }
    
    /*
    String jsonstr = (String) msg;
    jsonstr = jsonstr.substring(jsonstr.indexOf("{"),jsonstr.lastIndexOf("}")+1);
    System.out.println(jsonstr);
  
    Map<String, UpInfoForm> upInfoFormMap = new HashMap<>(3);
    InfoLoraModEndForm loraendpkt = new InfoLoraModEndForm();
    InfoFSKModEndForm fskendpkt = new InfoFSKModEndForm();
    InfoGateWayStatForm gwstat = new InfoGateWayStatForm();
  
    try {
        JSONObject json = JSONObject.parseObject(jsonstr);
        if (json.containsKey("rxpk")) {
            JSONArray rxpk_arry = json.getJSONArray("rxpk");
            for (int i = 0; i < rxpk_arry.size(); i++) {
                if (rxpk_arry.getJSONObject(i).getString("modu").equals("LORA")) {
  //      loraendpkt.setTime(rxpk_arry.getJSONObject(i).getString("time"));
                    loraendpkt.setTmst(rxpk_arry.getJSONObject(i).getDouble("tmst"));
                    if (rxpk_arry.getJSONObject(i).containsKey("tmms")) {
                        loraendpkt.setTmms(rxpk_arry.getJSONObject(i).getInteger("tmms"));
                    }
                    loraendpkt.setChan(rxpk_arry.getJSONObject(i).getInteger("chan"));
                    loraendpkt.setRfch(rxpk_arry.getJSONObject(i).getInteger("rfch"));
                    loraendpkt.setFreq((float) rxpk_arry.getJSONObject(i).getFloat("freq"));
                    loraendpkt.setStat(rxpk_arry.getJSONObject(i).getInteger("stat"));
                    loraendpkt.setModu(rxpk_arry.getJSONObject(i).getString("modu"));
                    loraendpkt.setDatr_lora(rxpk_arry.getJSONObject(i).getString("datr"));
                    loraendpkt.setCodr(rxpk_arry.getJSONObject(i).getString("codr"));
                    loraendpkt.setRssi(rxpk_arry.getJSONObject(i).getInteger("rssi"));
                    loraendpkt.setLsnr(rxpk_arry.getJSONObject(i).getInteger("lsnr"));
                    loraendpkt.setSize(rxpk_arry.getJSONObject(i).getInteger("size"));
                    loraendpkt.setData(Base64Utils.decode(rxpk_arry.getJSONObject(i).getString("data")));
                } else {
                    fskendpkt.setTime(rxpk_arry.getJSONObject(i).getString("time"));
                    fskendpkt.setTmst(rxpk_arry.getJSONObject(i).getInteger("tmst"));
                    if (rxpk_arry.getJSONObject(i).containsKey("tmms")) {
                        fskendpkt.setTmms(rxpk_arry.getJSONObject(i).getInteger("tmms"));
                    }
                    fskendpkt.setChan(rxpk_arry.getJSONObject(i).getInteger("chan"));
                    fskendpkt.setRfch(rxpk_arry.getJSONObject(i).getInteger("rfch"));
                    fskendpkt.setFreq(rxpk_arry.getJSONObject(i).getInteger("freq"));
                    fskendpkt.setStat(rxpk_arry.getJSONObject(i).getInteger("stat"));
                    fskendpkt.setModu(rxpk_arry.getJSONObject(i).getString("modu"));
                    fskendpkt.setDatr_fsk(rxpk_arry.getJSONObject(i).getInteger("datr"));
                    fskendpkt.setRssi(rxpk_arry.getJSONObject(i).getInteger("rssi"));
                    fskendpkt.setSize(rxpk_arry.getJSONObject(i).getInteger("size"));
                    fskendpkt.setData(Base64Utils.decode(rxpk_arry.getJSONObject(i).getString("data")));
                }
            }
        }
        if (json.containsKey("stat")) {
            JSONObject stat = json.getJSONObject("stat");
            gwstat.time = stat.getString("time");
  //  gwstat.lati = stat.getInteger("lati");
  //  gwstat.longe = stat.getInteger("long");
  //  gwstat.alti = stat.getInteger("alti");
            gwstat.rxnb = stat.getInteger("rxnb");
            gwstat.rxok = stat.getInteger("rxok");
            gwstat.rxfw = stat.getInteger("rxfw");
            gwstat.ackr = stat.getInteger("ackr");
            gwstat.dwnb = stat.getInteger("dwnb");
            gwstat.txnb = stat.getInteger("txnb");
            if (stat.containsKey("error")) {
                gwstat.ackr = stat.getInteger("ackr");
  //    gwstat.alti = stat.getInteger("alti");
                gwstat.dwnb = stat.getInteger("dwnb");
  //    gwstat.lati = stat.getInteger("lati");
  //    gwstat.longe = stat.getInteger("long");
                gwstat.rxfw = stat.getInteger("rxfw");
                gwstat.rxnb = stat.getInteger("rxnb");
                gwstat.rxok = stat.getInteger("rxok");
                gwstat.time = stat.getString("time");
                gwstat.txnb = stat.getInteger("txnb");
            }
        }
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
        upInfoFormMap.put("loraendpkt", loraendpkt);
        upInfoFormMap.put("fskendpkt", fskendpkt);
        upInfoFormMap.put("gwstat", gwstat);
        ctx.fireUserEventTriggered(loraendpkt);
    }
    */
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.info("与客户端({})通讯发生异常, 异常: {}", 
        ctx.channel().remoteAddress(), cause.getLocalizedMessage());
    super.exceptionCaught(ctx, cause);
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    logger.info("连接断开({})", ctx.channel().remoteAddress());
//    if (! StringUtils.isEmpty(deviceId)) {
//      deviceManageService.updateDeviceStatus(deviceId, DeviceStatusEnum.Offline);
//    }
    super.channelUnregistered(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      logger.info("连接失去活跃({})", ctx.channel().remoteAddress());
      super.channelInactive(ctx);
  }

  //-----------------------------------------

  //-------------------------------------------------------------------
  private static final byte PKT_PUSH_DATA = 0x00;
  private static final byte PKT_PUSH_ACK = 0x01;
  private static final byte PKT_PULL_DATA = 0x02;
  private static final byte PKT_PULL_RESP = 0x03;
  private static final byte PKT_PULL_ACK = 0x04;

  private void handlePKTPushData(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> list) {
    // 回应ACK
    ByteBuf byteBuf = datagramPacket.content().copy(0, 4);
    try {
      byteBuf.setByte(3, PKT_PUSH_ACK);
      ctx.channel().writeAndFlush(
          new DatagramPacket(Unpooled.copiedBuffer(byteBuf), new InetSocketAddress(
              datagramPacket.sender().getHostString(), LoRaWANInitializer.ClientPort)));
      // 去掉头部12字节
      list.add(new String(datagramPacket.content().toString(CharsetUtil.UTF_8).substring(6)));
    } finally {
      byteBuf.release();
    }
  }

  private void handlePKTPullData(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> list) throws InterruptedException {
    ByteBuf byteBuf1 = datagramPacket.content().copy(0, 4);
    try {
      byteBuf1.setByte(3, PKT_PULL_ACK);
      ctx.channel().writeAndFlush(
          new DatagramPacket(Unpooled.copiedBuffer(byteBuf1), new InetSocketAddress(
              datagramPacket.sender().getHostString(), LoRaWANInitializer.ClientPort)));
      byte[] buffer = new byte[4];
      // 如果是堆缓冲区
      if (datagramPacket.content().hasArray()) {
        buffer = datagramPacket.content().array();
      } else {
        // 如果是直接缓冲区
        datagramPacket.content().getBytes(0, buffer);
      }
      DownInfoForm info = null;
      byte[] down;
      buffer[3] = PKT_PULL_RESP;

      for (int i = 0; i < 8; i++) {
        info = (DownInfoForm) LoRaWANInitializer.queueDown.poll(1, TimeUnit.MICROSECONDS);
        if (info == null) {
          break;
        }
        // 构造 JSON 数据
        down = JsonUtils.toJson(info).getBytes();
        byte[] phyDown = new byte[down.length + 4];
        System.arraycopy(buffer, 0, phyDown, 0, 4);
        System.arraycopy(down, 0, phyDown, 4, down.length);
        ctx.channel()
            .writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(phyDown),
                new InetSocketAddress(datagramPacket.sender().getHostString(),
                    LoRaWANInitializer.ClientPort)));
      }
    } finally {
      byteBuf1.release();
    }
  }

}
