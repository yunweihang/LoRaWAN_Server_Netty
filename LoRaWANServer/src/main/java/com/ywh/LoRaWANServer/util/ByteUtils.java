package com.ywh.LoRaWANServer.util;


public class ByteUtils {
	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: BCD码
	 * @结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
	}

	/**
	 * @功能: 10进制串转为BCD码
	 * @参数: 10进制串
	 * @结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}
	
	/**
	 * 整数类型(支持10进制、8进制、16进制)转为二进制字符串，二进制以01表示。
	 * @param num
	 * @return
	 */
	public static String intToBinaryString(int num) {
		String binaryStr = Integer.toBinaryString(num);
		int supplementNum = 8-binaryStr.length();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<supplementNum;i++) {
			sb.append("0");
		}
		binaryStr = sb.toString() + binaryStr;
		
		return binaryStr;
		
	}
	
	/**
	 * 字节byte转为二进制字符串，并判断指定位是否为1
	 * @param num
	 * @return
	 */
	public static boolean judgeBitIf1(byte tByte,int index) {
	  String byteStr = Integer.toBinaryString((tByte & 0xFF) + 0x100).substring(1);
	  char c = byteStr.charAt(index);
	  if(c == '1') {
	    return true;
	  }
	  return false;
	}
	
	/**
   * 将字节转为二进制字符串，出去最高位后，将剩下的bit转为int
   * @param num
   * @return
   */
  public static int binaryString2Int(byte tByte) {
    String byteStr = Integer.toBinaryString((tByte & 0xFF) + 0x100).substring(1);
    String binaryStr = byteStr.substring(1);
    int num = Integer.parseInt(binaryStr,2);
    return num;
  }
	
	/**
	 * 将"."连接的数字字符串转为对应的字节数组
	 * @param str
	 * @return
	 */
	public static byte[] strNumberToByte(String str) {
		String[] strSp = str.split("\\.");
		byte[] b = new byte[strSp.length];
		for(int i=0;i<strSp.length;i++) {
			b[i] = (byte)Integer.parseInt(strSp[i]);
		}
		return b;
	}
	
  /**
   * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和intToBytes（）配套使用
   * @param src byte数组
   * @param offset 从数组的第offset位开始
   * @return int数值
   */   
	public static int bytesToInt(byte[] src, int offset) {  
	    int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;  
	}  
	  
  /**
   * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
   */
	public static int bytesToInt2(byte[] src, int offset) {  
	    int value;    
	    value = (int) ( ((src[offset] & 0xFF)<<24)  
	            |((src[offset+1] & 0xFF)<<16)  
	            |((src[offset+2] & 0xFF)<<8)  
	            |(src[offset+3] & 0xFF));  
	    return value;  
	}  
	
	/**
   * 二进制字符串转换为byte数组,每个字节以","隔开
   */
  public static byte[] binStrToByteArr(String binStr) {
      String[] temp = binStr.split(",");
      byte[] b = new byte[temp.length];
      for (int i = 0; i < b.length; i++) {
          b[i] = Long.valueOf(temp[i], 2).byteValue();
      }
      return b;
  }

}
