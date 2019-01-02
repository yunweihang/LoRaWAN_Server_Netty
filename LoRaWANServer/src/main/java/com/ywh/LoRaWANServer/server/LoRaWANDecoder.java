
package com.ywh.LoRaWANServer.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ywh.LoRaWANServer.config.LoRaConstant;
import com.ywh.LoRaWANServer.enums.MTypeEnum;
import com.ywh.LoRaWANServer.packet.PHYLayer;
import com.ywh.LoRaWANServer.packet.PHYPayload;
import com.ywh.LoRaWANServer.packet.mac.FHDR;
import com.ywh.LoRaWANServer.packet.phy.JoinAccept;
import com.ywh.LoRaWANServer.packet.phy.JoinRequest;
import com.ywh.LoRaWANServer.packet.phy.MACPayload;
import com.ywh.LoRaWANServer.packet.phy.MHDR;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * LoRaWAN规范(协议)解码器，阅读本类源码需要结合LoRaWAN规范一起阅读
 * LoRaWAN规范: LoRaWANtm_specification_-v1.1.pdf
 */
public class LoRaWANDecoder extends MessageToMessageDecoder<DatagramPacket> {
  private final Logger logger = LoggerFactory.getLogger(LoRaWANDecoder.class);

  @Override
  protected void decode(ChannelHandlerContext ctx, DatagramPacket dataPacket, List<Object> outList)
      throws Exception {
    ByteBuf byteBuf = dataPacket.content();
    logger.info("Received data: {}", ByteBufUtil.hexDump(byteBuf));
    PHYLayer phyLayer = new PHYLayer();
    phyLayer.setPreamble(byteBuf.readByte());
    phyLayer.setPhdr(byteBuf.readByte());
    byte[] phdrCrc = new byte[1];
    byteBuf.readBytes(phdrCrc);
    phyLayer.setPhdrCrc(phdrCrc);
    
    PHYPayload phyPayload = new PHYPayload();
    phyLayer.setPhyPayload(phyPayload);
    byte btMhdr = byteBuf.readByte();
    MHDR mhdr = new MHDR(btMhdr);
    phyPayload.setMhdr(mhdr);
    
    MTypeEnum mtypeEnum = MTypeEnum.getMTypeEnum(mhdr.getMtype());
    switch (mtypeEnum) {
      case JoinRequest:
        decodeJoinRequest(phyPayload, dataPacket);
        decodeReadMIC(phyPayload, byteBuf);
        break;
      case JoinAccept:
        decodeJoinAccept(phyPayload, dataPacket);
        break;
      case UnconfirmedDataUp:
      case UnconfirmedDataDown:
      case ConfirmedDataUp:
      case ConfirmedDataDown:
        decodeMACPayload(phyPayload, dataPacket);
        decodeReadMIC(phyPayload, byteBuf);
        break;
      case RFU:
        logger.info("recived mtype: RFU");
        break;
      case Proprietary:
        logger.info("received mtype: Proprietary");
        break;
      default:
        logger.error("cannt parse mtype: {}", mhdr.getMtype());
        break;
    }

    decodeReadPHYLayerCRC(phyLayer, byteBuf);
    outList.add(phyLayer);
  }

  private void decodeJoinRequest(PHYPayload phyPayload, DatagramPacket dataPacket) {
    ByteBuf byteBuf = dataPacket.content();
    JoinRequest joinRequest = new JoinRequest();
    phyPayload.setJoinRequest(joinRequest);
    
    byte[] joinEUI = new byte[8];
    byteBuf.readBytes(joinEUI);
    joinRequest.setJoinEUI(joinEUI);
    byte[] devEUI = new byte[8];
    byteBuf.readBytes(devEUI);
    joinRequest.setDevEUI(devEUI);
    byte[] devNonce = new byte[2];
    byteBuf.readBytes(devNonce);
    joinRequest.setDevNonce(devNonce);
  }

  private void decodeJoinAccept(PHYPayload phyPayload, DatagramPacket dataPacket) {
    ByteBuf byteBuf = dataPacket.content();
    JoinAccept joinAccept = new JoinAccept();
    phyPayload.setJoinAccept(joinAccept);

    byte[] joinNonce = new byte[3];
    byteBuf.readBytes(joinNonce);
    joinAccept.setJoinNonce(joinNonce);
    byte[] netID = new byte[3];
    byteBuf.readBytes(netID);
    joinAccept.setNetID(netID);
    byte[] devAddr = new byte[4];
    byteBuf.readBytes(devAddr);
    joinAccept.setDevAddr(devAddr);
    joinAccept.setDlSettings(byteBuf.readByte());
    joinAccept.setRxDelay(byteBuf.readByte());

    if (LoRaConstant.JoinAcceptHasCFList) {
      byte[] cfList = new byte[16];
      byteBuf.readBytes(cfList);
      joinAccept.setCfList(cfList);
    }
  }

  private void decodeMACPayload(PHYPayload phyPayload, DatagramPacket dataPacket) {
    ByteBuf byteBuf = dataPacket.content();
    MACPayload macPayload = new MACPayload();
    phyPayload.setMacPayload(macPayload);
    //
    FHDR fhdr = new FHDR();
    macPayload.setFhdr(fhdr);
    decodeReadFHDR(fhdr, byteBuf);
    
    macPayload.setFport(byteBuf.readByte());
    
    int frmPayloadLen = 0;
    if (frmPayloadLen > 0) {
      byte[] frmPayload = new byte[frmPayloadLen];
      byteBuf.readBytes(frmPayload);
      macPayload.setFrmPayload(frmPayload);
    }
  }

  private void decodeReadFHDR(FHDR fhdr, ByteBuf byteBuf) {
    byte[] devAddr = new byte[4];
    byteBuf.readBytes(devAddr);
    fhdr.setDevAddr(devAddr);
    byte fctrl = byteBuf.readByte();
    fhdr.setFctrl(fctrl);
    byte[] fcnt = new byte[4];
    byteBuf.readBytes(fcnt);
    fhdr.setFcnt(fcnt);
    byte foptsLen = (byte)(fctrl & 0x0F);
    if (foptsLen > 0) {
      byte[] fOpts = new byte[foptsLen];
      byteBuf.readBytes(fOpts);
      fhdr.setfOpts(fOpts);
    }
  }

  private void decodeReadMIC(PHYPayload phyPayload, ByteBuf byteBuf) {
    byte[] mic = new byte[2];
    byteBuf.readBytes(mic);
    phyPayload.setMic(mic);
  }

  private void decodeReadPHYLayerCRC(PHYLayer phyLayer, ByteBuf byteBuf) {
    byte[] crc = new byte[2];
    byteBuf.readBytes(crc);
    phyLayer.setCrc(crc);
  }

}
