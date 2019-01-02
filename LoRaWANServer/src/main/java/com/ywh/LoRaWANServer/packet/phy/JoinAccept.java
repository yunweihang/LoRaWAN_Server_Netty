package com.ywh.LoRaWANServer.packet.phy;

public class JoinAccept {
  byte[] joinNonce; // 3 bytes, appNonce
  byte[] netID; // 3 bytes, Home_NetID
  byte[] devAddr; // 4 bytes

  /**
   * -----------------------------------------------------
   * | Bit#  |     7      |    [6..4]    | [3..0]        |
   * | FCtrl |   OptNeg   |    OptNeg    | RX2 Data rate |
   * -----------------------------------------------------
   */
  byte dlSettings; // 1 bytes

  byte rxDelay; // 1 byte
  
  byte[] cfList; // 16 bytes
  
  CFList cnCFList; // 16 bytes

  //--------------------------------------------------------
  
  public byte[] getJoinNonce() {
    return joinNonce;
  }

  public void setJoinNonce(byte[] joinNonce) {
    this.joinNonce = joinNonce;
  }

  public byte[] getNetID() {
    return netID;
  }

  public void setNetID(byte[] netID) {
    this.netID = netID;
  }

  public byte[] getDevAddr() {
    return devAddr;
  }

  public void setDevAddr(byte[] devAddr) {
    this.devAddr = devAddr;
  }

  public byte getDlSettings() {
    return dlSettings;
  }

  public void setDlSettings(byte dlSettings) {
    this.dlSettings = dlSettings;
  }

  public byte getRxDelay() {
    return rxDelay;
  }

  public void setRxDelay(byte rxDelay) {
    this.rxDelay = rxDelay;
  }

  public byte[] getCfList() {
    return cfList;
  }

  public void setCfList(byte[] cfList) {
    this.cfList = cfList;
  }

  public CFList getCnCFList() {
    return cnCFList;
  }

  public void setCnCFList(CFList cnCFList) {
    this.cnCFList = cnCFList;
  }

  // -------------------------------------------------------------

}
