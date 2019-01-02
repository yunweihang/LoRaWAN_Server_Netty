package com.ywh.LoRaWANServer.temp.domain;


public interface UpInfoForm {

	
	void saveData();
	/**
	 * 
	 * @return data, 即 macpayload
	 */
	byte[] getData();
	
	String getModu();
	
	/**
	 * 
	 * @return 系统信息
	 */
	String getSysInfo();
	
	
}
