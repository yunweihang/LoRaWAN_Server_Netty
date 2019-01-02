package com.ywh.LoRaWANServer.enums;

public enum MTypeEnum {

  // 000, 请求入网，无线激活过程使用，具体见章节6.2
  JoinRequest((byte)0x00, "Join Request"), 
  // 001, 同意入网，无线激活过程使用，具体见章节6.2
  JoinAccept((byte)0x01, "Join Accept"),  
  // 010, 无需确认的上行消息，接收者不必回复
  UnconfirmedDataUp((byte)0x02, "Unconfirmed Data Up"), 
  // 011, 无需确认的下行消息，接收者不必回复
  UnconfirmedDataDown((byte)0x03, "Unconfirmed Data Down"), 
  // 100, 需要确认的上行消息，接收者必须回复
  ConfirmedDataUp((byte)0x04, "Confirmed Data Up"), 
  // 101, 需要确认的下行消息，接收者必须回复
  ConfirmedDataDown((byte)0x05, "Confirmed Data Down"), 
  // 110, 保留
  RFU((byte)0x06, "RFU"), 
  // 111, 用来实现自定义格式的消息，交互的设备之间必须有相同的处理逻辑，不能和标准消息互通
  Proprietary((byte)0x07, "Proprietary");
  
  private String name;
  private byte mtype;

  public static MTypeEnum getMTypeEnum(byte mtype) {
    MTypeEnum[] types = MTypeEnum.values();
    // 遍历枚举
    for (MTypeEnum mtypeEnum : types) {
      // 当传入名称和枚举的名称相等
      if (mtype == mtypeEnum.mtype) {
        return mtypeEnum;
      }
    }
    return null;
  }
  
  MTypeEnum(byte mtype, String name) {
    this.name = name;
    this.mtype = mtype;
  }

  public String getName() {
    return name;
  }

  public byte getMtype() {
    return mtype;
  }

}
