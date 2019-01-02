package com.ywh.LoRaWANServer.util;



import com.ywh.LoRaWANServer.config.LoRaConstant;
import com.ywh.LoRaWANServer.packet.mac_part.MacConfirmedDataDownForm;
import com.ywh.LoRaWANServer.packet.mac_part.MacConfirmedDataUpForm;
import com.ywh.LoRaWANServer.packet.mac_part.MacPktForm;
import com.ywh.LoRaWANServer.packet.mac_part.MacUnconfirmedDataDownForm;
import com.ywh.LoRaWANServer.packet.mac_part.MacUnconfirmedDataUpForm;
import com.ywh.LoRaWANServer.packet.mac_part.Mhdr;

public class MicCaculate {

    /**
     * @param macpktform: mac 层数据, 不含 mhdr, 这里需要根据 type 创建 mhdr
     * @param type:       用于判断 是否为 Accept 帧
     * @return 4 字节的 MIC
     */
    public static byte[] MicCaculate(MacPktForm macpktform, int type) {
        byte[] macbyte = macpktform.MacPkt2Byte();
        byte[] mic = new byte[4];
        byte[] data = new byte[macpktform.getLength() + 1];
        Base64Utils.myprintHex(macbyte);
        Mhdr mhdr = new Mhdr(type, 0, 0);
        System.arraycopy(mhdr.MhdrPktToByte(), 0, data, 0, mhdr.getLength());
        System.arraycopy(macbyte, 0, data, mhdr.getLength(), macbyte.length);

        byte[] nwkSKey = LoRaConstant.NwkSKey;
        byte[] appSKey = LoRaConstant.AppSKey;

        // MIC 计算
        switch (type) {
            case 0:        // Join Request
                byte[] requestb = new byte[data.length];
                System.arraycopy(data, 0, requestb, 0, data.length - 4);
                mic = LoRaMacCrypto.LoRaMacJionRequestComputeMic(requestb, requestb.length, LoRaConstant.APPKEY);
                break;
            case 1:        // Join Accept
                mic = LoRaMacCrypto.LoRaMacJoinAcceptComputeMic(data, data.length, LoRaConstant.APPKEY);
                break;
            case 2:        // UnConfirmed Data Up
//                nwkSKey = DatatypeConverter.parseHexBinary(redisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":NwkSKey"));
//				appSKey = DatatypeConverter.parseHexBinary(RedisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":AppSKey"));
                byte[] unConfirmedUpb = new byte[data.length - 4];
                System.arraycopy(data, 0, unConfirmedUpb, 0, data.length - 4);
                MacUnconfirmedDataUpForm macUnconfirmedDataUpForm = (MacUnconfirmedDataUpForm) macpktform;
                mic = LoRaMacCrypto.LoRaMacComputeMic(unConfirmedUpb, unConfirmedUpb.length, nwkSKey,
                        macUnconfirmedDataUpForm.DevAddr, (byte) 0x00,
                        macUnconfirmedDataUpForm.Fcnt);
                break;
            case 3:        // UnConfirmed Data Down
                MacUnconfirmedDataDownForm macUnconfirmedDataDownForm = (MacUnconfirmedDataDownForm) macpktform;
                Base64Utils.myprintHex(data);
//                nwkSKey = DatatypeConverter.parseHexBinary(redisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":NwkSKey"));
                Base64Utils.myprintHex(nwkSKey);
                System.out.println("ndajkshdjkashdjkash");
//				appSKey = DatatypeConverter.parseHexBinary(RedisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":AppSKey"));
                mic = LoRaMacCrypto.LoRaMacComputeMic(data, data.length, nwkSKey,
                        macUnconfirmedDataDownForm.DevAddr, (byte) 0x01,
                        macUnconfirmedDataDownForm.Fcnt);
                break;
            case 4:        // Confirmed Data Up
                byte[] confirmedUpb = new byte[data.length];
                System.arraycopy(data, 0, confirmedUpb, 0, data.length - 4);
//                nwkSKey = DatatypeConverter.parseHexBinary(redisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":NwkSKey"));
//				appSKey = DatatypeConverter.parseHexBinary(RedisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":AppSKey"));
                MacConfirmedDataUpForm macConfirmedDataUpForm = (MacConfirmedDataUpForm) macpktform;
                mic = LoRaMacCrypto.LoRaMacComputeMic(confirmedUpb, confirmedUpb.length, nwkSKey,
                        macConfirmedDataUpForm.DevAddr, (byte) 0x00,
                        macConfirmedDataUpForm.Fcnt);
                break;
            case 5:        // Confirmed Data Down
                MacConfirmedDataDownForm macConfirmedDataDownForm = (MacConfirmedDataDownForm) macpktform;
//                nwkSKey = DatatypeConverter.parseHexBinary(redisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":NwkSKey"));
//				appSKey = DatatypeConverter.parseHexBinary(RedisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":AppSKey"));
                mic = LoRaMacCrypto.LoRaMacComputeMic(data, data.length, nwkSKey, macConfirmedDataDownForm.DevAddr, (byte) 0x01, macConfirmedDataDownForm.Fcnt);
                break;
            case 6:        // RFU

                break;
            case 7:        // Proprietary

                break;
            default:
                mic = null;
//                nwkSKey = DatatypeConverter.parseHexBinary(redisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":NwkSKey"));
//                appSKey = DatatypeConverter.parseHexBinary(RedisDB.jedisPool.getResource().get(ObjectToString.byteToStrWith(macpktform.getDevAddr()) + ":AppSKey"));
                break;
        }
        return mic;
    }
}


