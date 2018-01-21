package com.alexander.v.gateway.model;

import java.util.List;

/**
 * 
 * @ClassName: Vehicle
 * @Description: TODO
 * @author chenyun
 * @date 2017年6月14日 下午8:25:52
 * 
 * @version 1.0.0
 */
public class Vehicle {

	/**
	 * 车架号
	 */
	private String vin;

	/**
	 * SIM卡ICCID号（ICCID应为终端从SIM卡获取的值，不应人为填写或修改）。
	 */
	private String iccid;

	/**
	 * 可充电储能系统编码
	 */
	private List<String> chargingSystems;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public List<String> getChargingSystems() {
		return chargingSystems;
	}

	public void setChargingSystems(List<String> chargingSystems) {
		this.chargingSystems = chargingSystems;
	}
}
