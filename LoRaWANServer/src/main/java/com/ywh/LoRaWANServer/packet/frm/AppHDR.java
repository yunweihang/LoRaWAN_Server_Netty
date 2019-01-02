package com.ywh.LoRaWANServer.packet.frm;

/**
 * FRMPayload里面应用自定义的私有协议头
 */
public class AppHDR {
  private byte version; // 协议版本号
  private byte[] mac; // 6 bytes, MAC
  private byte requestFlag; // 请求/应答标志, 0:请求, 1:应答
  private int packetNo; // 4 bytes, 包号
  private byte dataLen; // (ChargePayload)数据长度
  private byte packetFlag; // 1 byte, 分包标志
  private short crc16; // 2 bytes, CRC16

  // --------------------------------------
  
  public byte getVersion() {
    return version;
  }

  public void setVersion(byte version) {
    this.version = version;
  }

  public byte[] getMac() {
    return mac;
  }

  public void setMac(byte[] mac) {
    this.mac = mac;
  }

  public byte getRequestFlag() {
    return requestFlag;
  }

  public void setRequestFlag(byte requestFlag) {
    this.requestFlag = requestFlag;
  }

  public int getPacketNo() {
    return packetNo;
  }

  public void setPacketNo(int packetNo) {
    this.packetNo = packetNo;
  }

  public byte getDataLen() {
    return dataLen;
  }

  public void setDataLen(byte dataLen) {
    this.dataLen = dataLen;
  }

  public short getCrc16() {
    return crc16;
  }

  public void setCrc16(short crc16) {
    this.crc16 = crc16;
  }

  public byte getPacketFlag() {
    return packetFlag;
  }

  public void setPacketFlag(byte packetFlag) {
    this.packetFlag = packetFlag;
  }

}
