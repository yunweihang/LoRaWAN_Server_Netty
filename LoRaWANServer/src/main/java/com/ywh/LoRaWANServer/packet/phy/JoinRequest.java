package com.ywh.LoRaWANServer.packet.phy;

public class JoinRequest {
  /**
   * AppEUI是一个类似IEEE EUI64的全球唯一ID，标识终端的应用提供者。
   * APPEUI在激活流程开始前就存储在终端中。
   */
  byte[] joinEUI; // 8 bytes, appEUI
  
  /**
   * 终端 ID (DevEUI) 
   * DevEUI 是一个类似IEEE EUI64的全球唯一ID，标识唯一的终端设备。
   */
  byte[] devEUI; // 8 bytes
  
  /**
   * DevNonce 2字节的随机数，用于生成随机的AppSKey和NwkSKey
   */
  byte[] devNonce; // 2 bytes

  //-----------------------------------------------------
  
  public byte[] getJoinEUI() {
    return joinEUI;
  }

  public void setJoinEUI(byte[] joinEUI) {
    this.joinEUI = joinEUI;
  }

  public byte[] getDevEUI() {
    return devEUI;
  }

  public void setDevEUI(byte[] devEUI) {
    this.devEUI = devEUI;
  }

  public byte[] getDevNonce() {
    return devNonce;
  }

  public void setDevNonce(byte[] devNonce) {
    this.devNonce = devNonce;
  }
  
}
