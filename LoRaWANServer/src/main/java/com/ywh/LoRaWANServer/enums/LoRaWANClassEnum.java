package com.ywh.LoRaWANServer.enums;

/**
 */
public enum LoRaWANClassEnum {
  /*
   * 双向传输终端(Class A)： Class A 的终端在每次上行后都会紧跟两个短暂 的下行接收窗口，以此实现双向传输。
   * 传输时隙是由终端在有传输需要时 安排，附加一定的随机延时(即ALOHA协议)。
   * 这种Class A 操作是最省电的， 要求应用在终端上行传输后的很短时间内进行服务器的下行传输。
   * 服务器 在其他任何时间进行的下行传输都得等终端的下一次上行。
   */
  ClassA, 
  
  /*
   * 划定接收时隙的双向传输终端(Class B)： Class B 的终端会有更多的接收 时隙。
   * 除了Class A 的随机接收窗口，Class B 设备还会在指定时间打开别 的接收窗口。
   * 为了让终端可以在指定时间打开接收窗口，终端需要从网关 接收时间同步的信标 Beacon。这使得服务器可以知道终端正在监听。
   */
  ClassB, 
  
  /*
   * 最大化接收时隙的双向传输终端(Class C)： Class C 的终端基本是一直打 开着接收窗口，只在发送时短暂关闭。
   * Class C 的终端会比 Class A 和 Class B 更加耗电，但同时从服务器下发给终端的时延也是最短的。 
   */
  ClassC
}
