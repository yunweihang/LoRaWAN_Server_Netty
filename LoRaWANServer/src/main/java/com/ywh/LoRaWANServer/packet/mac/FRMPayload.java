package com.ywh.LoRaWANServer.packet.mac;

import com.ywh.LoRaWANServer.packet.frm.AppHDR;

public class FRMPayload {
  AppHDR appHDR;     // 应用自定义的私有协议头
  byte[] appPayload; // 应用自定义的私有协议载荷

  // ---------------------------------------------------

  public AppHDR getAppHDR() {
    return appHDR;
  }

  public void setAppHDR(AppHDR appHDR) {
    this.appHDR = appHDR;
  }

  public byte[] getAppPayload() {
    return appPayload;
  }

  public void setAppPayload(byte[] appPayload) {
    this.appPayload = appPayload;
  }

}
