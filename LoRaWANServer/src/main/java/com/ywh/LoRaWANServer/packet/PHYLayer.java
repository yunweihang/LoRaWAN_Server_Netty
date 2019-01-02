package com.ywh.LoRaWANServer.packet;

public class PHYLayer {
  private byte preamble; // 1 byte ?
  private byte phdr; // 1 byte ?
  private byte phdrCrc[]; // 2 byte ?
  private PHYPayload phyPayload;
  private byte[] crc; // 只在上行消息中有, 下行消息无该字段；

  //------------------------------------------------------------------
  public byte getPreamble() {
    return preamble;
  }

  public void setPreamble(byte preamble) {
    this.preamble = preamble;
  }

  public byte getPhdr() {
    return phdr;
  }

  public void setPhdr(byte phdr) {
    this.phdr = phdr;
  }

  public byte[] getPhdrCrc() {
    return phdrCrc;
  }

  public void setPhdrCrc(byte[] phdrCrc) {
    this.phdrCrc = phdrCrc;
  }

  public PHYPayload getPhyPayload() {
    return phyPayload;
  }

  public void setPhyPayload(PHYPayload phyPayload) {
    this.phyPayload = phyPayload;
  }

  public byte[] getCrc() {
    return crc;
  }

  public void setCrc(byte[] crc) {
    this.crc = crc;
  }
}
