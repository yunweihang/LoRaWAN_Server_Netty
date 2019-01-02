package com.ywh.LoRaWANServer.enums;

public enum MACCommandEnum {
  // 以下MAC Command 更新到： LoRaWANtm_specification_-v1.1.pdf
  LinkCheckReq((byte)0x02, "LinkCheckReq", MACCommandEnum.EndDeviceFlag, "终端利用这个命令来判断网络连接质量"),
  LinkCheckAns((byte)0x02, "LinkCheckAns", MACCommandEnum.GatewayFlag, "LinkCheckReq的回复。包含接收信号强度，告知终端接收质量"),
  LinkADRReq((byte)0x03, "LinkADRReq", MACCommandEnum.GatewayFlag, "向终端请求改变数据速率，发射功率，重传率以及信道"),
  LinkADRAns((byte)0x03, "LinkADRAns", MACCommandEnum.EndDeviceFlag, "LinkADRReq的回复"),
  DutyCycleReq((byte)0x04, "DutyCycleReq", MACCommandEnum.GatewayFlag, "向终端设置发送的最大占空比"),
  DutyCycleAns((byte)0x04, "DutyCycleAns", MACCommandEnum.EndDeviceFlag, "DutyCycleReq的回复"),
  RXParamSetupReq((byte)0x05, "RXParamSetupReq", MACCommandEnum.GatewayFlag, "RXParamSetupReq的回复"),
  RXParamSetupAns((byte)0x05, "RXParamSetupAns", MACCommandEnum.EndDeviceFlag, "RXParamSetupReq的回复"),
  DevStatusReq((byte)0x06, "DevStatusReq", MACCommandEnum.GatewayFlag, "向终端查询其状态"),
  DevStatusAns((byte)0x06, "DevStatusAns", MACCommandEnum.EndDeviceFlag, "返回终端设备的状态，即电池余量和链路解调预算"),
  NewChannelReq((byte)0x07, "NewChannelReq", MACCommandEnum.GatewayFlag, "创建或修改 1个射频信道 定义"),
  NewChannelAns((byte)0x07, "NewChannelAns", MACCommandEnum.EndDeviceFlag, "NewChannelReq的回复"),
  RXTimingSetupReq((byte)0x08, "RXTimingSetupReq", MACCommandEnum.GatewayFlag, "设置接收时隙的时间"),
  RXTimingSetupAns((byte)0x08, "RXTimingSetupAns", MACCommandEnum.EndDeviceFlag, "RXTimingSetupReq的回复"),
  TxParamSetupReq((byte)0x09, "TxParamSetupReq", MACCommandEnum.GatewayFlag, ""),
  TxParamSetupAns((byte)0x09, "TxParamSetupAns", MACCommandEnum.EndDeviceFlag, "Acknowledges TxParamSetupReq command"),
  DlChannelReq((byte)0x0A, "DlChannelReq", MACCommandEnum.GatewayFlag, ""),
  DlChannelAns((byte)0x0A, "DlChannelAns", MACCommandEnum.EndDeviceFlag, ""),
  RekeyInd((byte)0x0B, "RekeyInd", MACCommandEnum.EndDeviceFlag, ""),
  RekeyConf((byte)0x0B, "RekeyConf", MACCommandEnum.GatewayFlag, ""),
  ADRParamSetupReq((byte)0x0C, "ADRParamSetupReq", MACCommandEnum.GatewayFlag, ""),
  ADRParamSetupAns((byte)0x0C, "ADRParamSetupAns", MACCommandEnum.EndDeviceFlag, ""),
  DeviceTimeReq((byte)0x0D, "DeviceTimeReq", MACCommandEnum.EndDeviceFlag, ""),
  DeviceTimeAns((byte)0x0D, "DeviceTimeAns", MACCommandEnum.GatewayFlag, "Acknowledges ADRParamSetupReq command "),
  ForceRejoinReq((byte)0x0E, "ForceRejoinReq", MACCommandEnum.GatewayFlag, "Used by an end-device to request the current date and time "),
  RejoinParamSetupReq((byte)0x0F, "RejoinParamSetupReq", MACCommandEnum.GatewayFlag, "Sent by the network, answer to the DeviceTimeReq request "),
  RejoinParamSetupAns((byte)0x0F, "RejoinParamSetupAns", MACCommandEnum.EndDeviceFlag, "Sent by the network, ask the device t");
  // 0x80~0xFF 私有 给私有网络命令拓展做预留

  public static final byte EndDeviceFlag = (byte)0x00; 
  public static final byte GatewayFlag = (byte)0x01; 

  private byte cid;
  private String command;
  private byte from; // 由谁发送: 0:终端, 1:网关
  private String desc; // 描述

  MACCommandEnum(byte cid, String command, byte from, String desc) {
    this.cid = cid;
    this.command = command;
    this.from = from;
    this.desc = desc;
  }

  public byte getCid() {
    return cid;
  }

  public void setCid(byte cid) {
    this.cid = cid;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public byte getFrom() {
    return from;
  }

  public void setFrom(byte from) {
    this.from = from;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
