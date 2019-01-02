package com.ywh.LoRaWANServer.temp.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ywh.LoRaWANServer.server.LoRaWANInitializer;
import com.ywh.LoRaWANServer.util.ByteArrayAndInt;
import com.ywh.LoRaWANServer.util.CalPingoffset;

/**
 * Created by lynch on 2018/11/1. <br>
 **/
public class ClassBNodeOpens {

    public static long slotsStart() {
      LoRaWANInitializer.beacon_time = CalPingoffset.beacontime();
        long millBeacon_time = (long) (ByteArrayAndInt.byteArrayToInt(LoRaWANInitializer.beacon_time)) * 1000;
//        System.out.println(millBeacon_time);
//        System.out.println("数据：" + millBeacon_time + "   " + UDPLoRaInitializer.pingOffset + "   " + 
//          UDPLoRaInitializer.beacon_reserved);
        long finaltime = 0;
        for (int i = 0; i < LoRaWANInitializer.pingNb; i++) {
            double time = millBeacon_time + 
                (LoRaWANInitializer.pingOffset + i * LoRaWANInitializer.ping_period) * LoRaWANInitializer.slotLen + 
                LoRaWANInitializer.beacon_reserved;
            BigDecimal bigDecimal = new BigDecimal(time);
            String strtime = bigDecimal.toPlainString();
            // System.out.println(strtime);
            finaltime = Long.parseLong(strtime);
            //System.out.println("time:" + finaltime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            double cha = (double) 315964800 * 1000;
            //System.out.println(finaltime + cha);
            String sd = sdf.format(new Date((long) (finaltime + cha)));   // 时间戳转换成时间
            System.out.println("T：" + sd);
            System.out.println("T finaltime:" + finaltime);
        }
        return finaltime;
    }

}
