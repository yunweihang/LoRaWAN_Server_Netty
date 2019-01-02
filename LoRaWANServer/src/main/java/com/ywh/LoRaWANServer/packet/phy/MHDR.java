package com.ywh.LoRaWANServer.packet.phy;

public class MHDR {

  /** MType 表示消息类型 */
  private byte mtype; // bits[7..5]
  
  /** rfu */
  private byte rfu; // bits[4..2]

  /** 
   * Major 表示LoRaWAN主版本号，指明帧格式所遵循的编码规则 
   * 00 : LoRaWAN R1
   * 01..11 : 保留（RFU）
   **/
  private byte major; // bits[1..0]

  public MHDR() {
    
  }
  
  public MHDR(byte btMhdr) {
    mtype = (byte)((btMhdr >> 5) & 0x1F); // bits[7..5]
    rfu = (byte)((btMhdr >> 2) & 0x07);   // bits[4..2]
    major = (byte)(btMhdr & 0x03);        // bits[1..0]
  }

  //-------------------------------------------------------------------
  
  public byte getMHDR() {
    return (byte)((mtype << 5) | (rfu << 2) | major);
  }

  public byte getMtype() {
    return mtype;
  }

  public void setMtype(byte mtype) {
    this.mtype = mtype;
  }

  public byte getRfu() {
    return rfu;
  }

  public void setRfu(byte rfu) {
    this.rfu = rfu;
  }

  public byte getMajor() {
    return major;
  }

  public void setMajor(byte major) {
    this.major = major;
  }
  
}
