package com.ywh.LoRaWANServer.packet;

import com.ywh.LoRaWANServer.packet.phy.JoinAccept;
import com.ywh.LoRaWANServer.packet.phy.JoinRequest;
import com.ywh.LoRaWANServer.packet.phy.MACPayload;
import com.ywh.LoRaWANServer.packet.phy.MHDR;

/**
 * PHY 可能是以下三种之一:
 * MHDR + MACPayload + MIC
 * MHDR + Join-Request + MIC
 * MHDR + Join-Response + MIC
 */
public class PHYPayload {

  /**
   * MHDR
   * ------------------------------------
   * | Bit#  |  7..5  |  4..2  |  1..0  |
   * | MHDR  |  MType |  RFU   |  Major |
   * ------------------------------------
   * 
  **/
//  byte mhdr; // 1 byte
  private MHDR mhdr;  // 1 byte, MIC计算该内容
  private MACPayload macPayload; // MIC计算该内容
  private JoinRequest joinRequest;
  private JoinAccept joinAccept;
  
  /**
   * 消息一致性校验码 (MIC)
   * 对整个消息的所有字段进行计算（AES签名算法CMAC）得到消息一致性校验码（MIC）
   * msg = MHDR | FHDR | FPort | FRMPayload
   */
  private byte[]  mic; // 4 bytes

  //---------------------------------------------------------
  
  public MHDR getMhdr() {
    return mhdr;
  }

  public void setMhdr(MHDR mhdr) {
    this.mhdr = mhdr;
  }

  public MACPayload getMacPayload() {
    return macPayload;
  }

  public void setMacPayload(MACPayload macPayload) {
    this.macPayload = macPayload;
  }

  public JoinRequest getJoinRequest() {
    return joinRequest;
  }

  public void setJoinRequest(JoinRequest joinRequest) {
    this.joinRequest = joinRequest;
  }

  public JoinAccept getJoinAccept() {
    return joinAccept;
  }

  public void setJoinAccept(JoinAccept joinAccept) {
    this.joinAccept = joinAccept;
  }

  public byte[] getMic() {
    return mic;
  }

  public void setMic(byte[] mic) {
    this.mic = mic;
  }
}
