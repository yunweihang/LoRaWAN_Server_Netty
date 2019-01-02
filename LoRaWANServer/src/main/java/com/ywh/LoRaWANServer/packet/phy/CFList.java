package com.ywh.LoRaWANServer.packet.phy;

import java.util.Arrays;

/**
 * reference: lorawan-regional-parameters-v1.1ra,
 * 2.6.4 CN470-510 J oin-accept CFList
 */
public class CFList {

  byte[] chMask0;    // 2 bytes, appNonce
  byte[] chMask1;    // 2 bytes, appNonce
  byte[] chMask2;    // 2 bytes, appNonce
  byte[] chMask3;    // 2 bytes, appNonce
  byte[] chMask4;    // 2 bytes, appNonce
  byte[] chMask5 ;   // 2 bytes, appNonce
  byte[] RFU ;       // 3 bytes, appNonce
  byte cfListType;   // 1 bytes, appNonce

  public CFList()  {
    
  }
  
  public CFList(byte[] cfList)  {
    chMask0 = Arrays.copyOfRange(cfList, 0, 2);
    chMask1 = Arrays.copyOfRange(cfList, 2, 4);
    chMask2 = Arrays.copyOfRange(cfList, 4, 6);
    chMask3 = Arrays.copyOfRange(cfList, 6, 8);
    chMask4 = Arrays.copyOfRange(cfList, 8, 10);
    chMask5 = Arrays.copyOfRange(cfList, 10, 12);
    RFU = Arrays.copyOfRange(cfList, 12, 15);
    cfListType = cfList[15];
  }
  
  // ------------------------------------------------------

  public byte[] getChMask0() {
    return chMask0;
  }

  public void setChMask0(byte[] chMask0) {
    this.chMask0 = chMask0;
  }

  public byte[] getChMask1() {
    return chMask1;
  }

  public void setChMask1(byte[] chMask1) {
    this.chMask1 = chMask1;
  }

  public byte[] getChMask2() {
    return chMask2;
  }

  public void setChMask2(byte[] chMask2) {
    this.chMask2 = chMask2;
  }

  public byte[] getChMask3() {
    return chMask3;
  }

  public void setChMask3(byte[] chMask3) {
    this.chMask3 = chMask3;
  }

  public byte[] getChMask4() {
    return chMask4;
  }

  public void setChMask4(byte[] chMask4) {
    this.chMask4 = chMask4;
  }

  public byte[] getRFU() {
    return RFU;
  }

  public void setRFU(byte[] rFU) {
    RFU = rFU;
  }

  public byte getCfListType() {
    return cfListType;
  }

  public void setCfListType(byte cfListType) {
    this.cfListType = cfListType;
  }

}
