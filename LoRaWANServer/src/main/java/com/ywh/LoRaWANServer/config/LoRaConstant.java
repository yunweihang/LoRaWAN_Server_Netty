package com.ywh.LoRaWANServer.config;

public class LoRaConstant {

  /**  指定Join-Accept是否有CFList */
  public static boolean JoinAcceptHasCFList = false;
  
  /**
   * 应用密钥(AppKey) 
   * AppKey 是由应用程序拥有者分配给终端，很可能是由应用程序指定的根密钥来衍生的，并且受提供者控制。
   * 当终端通过空中激活方式加入网络，AppKey用来产生会话密钥NwkSKey和AppSKey，会话密钥分别用来加密和校验网络层和应用层数据。
   * DevNonce 2字节的随机数，用于生成随机的AppSKey和NwkSKey
   */
  public static final byte[] APPKEY = {
      (byte) 0x90, (byte) 0xCC, (byte) 0xA5, (byte) 0x0E
      , (byte) 0xA0, (byte) 0x99, (byte) 0xEA, (byte) 0xB1
      , (byte) 0x1A, (byte) 0xF5, (byte) 0xDF, (byte) 0xA4
      , (byte) 0x9F, (byte) 0x79, (byte) 0x25, (byte) 0x0F};

  /**
   * 应用层数据加解密-密钥
   */
  public static final byte[] AppSKey = APPKEY;

  /**
   * 网络层数据加解密-密钥
   * NwkSKey被终端和网络服务器用来计算和校验所有消息的MIC，以保证数据完整性。也用来对单独MAC的数据消息载荷进行加解密。
   */
  public static final byte[] NwkSKey = {
      (byte) 0xB1, (byte) 0x4B, (byte) 0xCD, (byte) 0x73
      , (byte) 0x5B, (byte) 0x50, (byte) 0x20, (byte) 0x5C
      , (byte) 0x76, (byte) 0xE4, (byte) 0x8C, (byte) 0x45
      , (byte) 0x12, (byte) 0x2A, (byte) 0x5F, (byte) 0x76
      
      };

  /**
   * 设备地址(DevAddr)
   * AppSKey被终端和网络服务器用来对应用层消息进行加解密。当应用层消息载荷有MIC时，也可以用来计算和校验该应用层MIC。
   */
  public static final byte[] DevAddr = {(byte) 0xAF,  (byte) 0x3A, (byte) 0x65, (byte) 0x00};
  
}
