
package com.ywh.LoRaWANServer.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LoRaWANEncoder extends MessageToByteEncoder<Object> {

  @Override
  protected void encode(ChannelHandlerContext tcx, Object body, ByteBuf out) throws Exception {
  }

}
