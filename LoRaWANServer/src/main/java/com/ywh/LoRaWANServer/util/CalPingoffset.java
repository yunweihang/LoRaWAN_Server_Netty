package com.ywh.LoRaWANServer.util;

/**
 * 从网络时间获取beacon，它从1970年01月01日0h00m00s，System.currentTimeMillis()
 * GPS时起点为1980年1月6日0h00m00s
 **/
public class CalPingoffset {

    public static byte[] beacontime() {
        int SecondSinceEpoch1 = (int) (System.currentTimeMillis() / 1000 - 315964800);
        int mode = SecondSinceEpoch1 % 128;
        int SecondSinceEpoch2 = SecondSinceEpoch1 - mode;
        return ByteArrayAndInt.intToByteArray(SecondSinceEpoch2);
    }
}
