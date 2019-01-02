package com.ywh.LoRaWANServer.packet.mac_part;

public interface MacPktForm {
	
	public byte[] MacPkt2Byte();
	public int getLength();
	public byte[] getDevAddr();
	
	int getEndType();
}
