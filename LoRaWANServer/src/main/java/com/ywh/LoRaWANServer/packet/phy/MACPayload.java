package com.ywh.LoRaWANServer.packet.phy;

import com.ywh.LoRaWANServer.packet.mac.FHDR;

public class MACPayload {
  
  /** 帧头 */
  private FHDR fhdr; // 7..23 bytes
  
  /**
   * 端口字段(FPort), 可变
   * 如果帧载荷字段不为空，端口字段必须体现出来。端口字段有体现时，
   * FPort的值为0表示FRMPayload只包含了MAC命令； 
   * FPort的数值从1到223(0x01..0xDF)都是由应用层使用。
   * FPort = 224 专门为LoRaWAN Mac层测试协议服务。
   * FPort的值从224到255(0xE0..0xFF)是保留用做未来的标准应用拓展。
   * 
   * 使用哪种加密秘钥K取决于FPort：
   * Fport=0,使用NwkSKey(网络密钥); 
   * Fport=1..255,使用AppSKey(应用密钥);
   */
  private byte fport; // 0..1 byte, 可能没有该字段
  
  /** 
   * 帧载荷，可变
   * 如果帧数据中包含payload，要先对FRMPayload进行加密，再计算消息的一致性校验码（MIC）。
   * 加密方案使用基于IEEE 802.15.4/2006 Annex B [IEEE802154] 的AES加密，秘钥长度128位。
   * 使用哪种加密秘钥K取决于消息的FPort：Fport=0,使用NwkSKey(网络密钥); Fport=1..255,使用AppSKey(应用密钥);
   * 加密字段： pld = FRMPayload
   * 
   * 关于MAC命令序列：
   * 一条MAC命令由一个字节的命令ID（CID）和特定的命令序列组成，命令序列可以是空。
   * 一帧数据中可以包含任何MAC命令序列，MAC命令既可以放在FOpts中和正常数据一起发送；
   * 也可以放在FRMPayload中单独发送，此时FPort = 0，但不能同时在两个字段携带MAC命令。
   * 放在FOpts中的MAC命令不加密，并且不能超过15个字节。放在FRMPayload中的MAC命令必须加密，同时不能超过FRMPayload的最大长度。
   * 
   **/
//  private FRMPayload frmPayload;
  private byte[] frmPayload;

  //-------------------------------------------------------------------
  
  public FHDR getFhdr() {
    return fhdr;
  }

  public void setFhdr(FHDR fhdr) {
    this.fhdr = fhdr;
  }

  public byte getFport() {
    return fport;
  }

  public void setFport(byte fport) {
    this.fport = fport;
  }

  public byte[] getFrmPayload() {
    return frmPayload;
  }

  public void setFrmPayload(byte[] frmPayload) {
    this.frmPayload = frmPayload;
  }

//  public FRMPayload getFrmPayload() {
//    return frmPayload;
//  }
//
//  public void setFrmPayload(FRMPayload frmPayload) {
//    this.frmPayload = frmPayload;
//  }
  
  
}
