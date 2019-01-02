package com.ywh.LoRaWANServer.packet.mac;

/**
 * 帧头
 * @Class Name FHDR
 */
public class FHDR {
  
  /** 设备标识 */
  byte[] devAddr; // 4 bytes
  
  /**
   * 帧控制码
   * ---------------------------------------------------
   * For downlink frames the FCtrl is:
   * -----------------------------------------------------------
   * | Bit#  |   7   |     6     |   5   |    4     | [3..0]   |
   * | FCtrl |  ADR  |    RFU    |  ACK  | FPending | FOptsLen |
   * -----------------------------------------------------------
   * 
   * For uplink frames the FCtrl is:
   * -----------------------------------------------------------
   * | Bit#  |   7   |     6     |   5   |    4     | [3..0]   |
   * | FCtrl |  ADR  | ADRACKReq |  ACK  |    RFU   | FOptsLen |
   * -----------------------------------------------------------
   * 
   * ADR(Adaptive Data Rate), ADRACKReq 用于：自适应数据速率的控制
   *   如果ADR的位字段有置位，网络就会通过相应的MAC命令来控制终端设备的数据速率。如果ADR位没设置，网络则无视终端的接收信号强度，不再控制终端设备的数据速率。
   *   ADR位可以根据需要通过终端及网络来设置或取消。不管怎样，ADR机制都应该尽可能使能，帮助终端延长电池寿命和扩大网络容量。
   * 消息应答(ACK in FCtrl) 用于：消息应答。收到confirmed类型的消息时，接收端要回复一条应答消息(应答位ACK要进行置位)。
   *   如果发送者是终端，网络就利用终端发送操作后打开的两个接收窗口之一进行回复。如果发送者是网关，终端就自行决定是否发送应答。 
   *   应答消息只会在收到消息后回复发送，并且不重发。
   * 帧挂起位(FPending in FCtrl 只在下行有效):只在下行交互中使用，表示网关还有挂起数据等待下发，需要终端尽快发送上行消息来再打开一个接收窗口。
   * 帧可选项长度(FOptsLen in FCtrl, FOpts): FCtrl 字节中的FOptsLen位字段描述了整个帧可选项(FOpts)的字段长度。
   *   如果FOptsLen为0，则FOpts为空；如果FOptsLen不为0，即MAC命令放在FOpts字段，端口号不能为0（这时FPort要么为空，要么是一个非零值）
   */
  byte fctrl; // 1 byte

  /** 帧计数器 */
  byte[] fcnt; // 2 bytes

  /**
   * 帧可选项(Frame Options)
   * FOpts字段存放MAC命令，最长15字节。如果FOptsLen为0，则FOpts为空
   * 如果MAC命令在FOpts字段中体现，fport=0不能用(FPort要么不体现，要么非0)。
   * MAC命令不能同时出现在FRMPayload和FOpts中，如果出现了，设备丢掉该组数据。
   */
  byte[] fOpts; // 0..15 bytes, 可变长,最多15字节,由fctrl里面指定该字段长度

  //--------------------------------------------------
  
  public byte[] getDevAddr() {
    return devAddr;
  }

  public void setDevAddr(byte[] devAddr) {
    this.devAddr = devAddr;
  }

  public byte getFctrl() {
    return fctrl;
  }

  public void setFctrl(byte fctrl) {
    this.fctrl = fctrl;
  }

  public byte[] getFcnt() {
    return fcnt;
  }

  public void setFcnt(byte[] fcnt) {
    this.fcnt = fcnt;
  }

  public byte[] getfOpts() {
    return fOpts;
  }

  public void setfOpts(byte[] fOpts) {
    this.fOpts = fOpts;
  }
  
}
