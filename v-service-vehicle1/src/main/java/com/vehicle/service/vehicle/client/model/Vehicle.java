package com.vehicle.service.vehicle.client.model;

import java.io.Serializable;
import java.util.List;

public class Vehicle implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车架号
	 */
	private String vin;

	/**
	 * 车牌号
	 */
	private String lince;

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

	public String getLince() {
		return lince;
	}

	public void setLince(String lince) {
		this.lince = lince;
	}

}
