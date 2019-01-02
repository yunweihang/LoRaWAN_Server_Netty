package com.ywh.LoRaWANServer.util;

public class ByteArrayAndInt {
  
    public static byte[] intToByteArray(int IntNum) {
        //低位在前，高位在后
        return new byte[]{
                (byte) (IntNum & 0xFF),
                (byte) ((IntNum >> 8) & 0xFF),
                (byte) ((IntNum >> 16) & 0xFF),
                (byte) ((IntNum >> 24) & 0xFF)};
    }

    public static int byteArrayToInt(byte[] bytes) {
        int reInt = 0;
        if (bytes != null) {
            reInt = bytes[0] & 0xFF |
                    (bytes[1] & 0XFF) << 8 |
                    (bytes[2] & 0xFF) << 16 |
                    (bytes[3] & 0xFF) << 24;
        }
        return reInt;
    }
}
