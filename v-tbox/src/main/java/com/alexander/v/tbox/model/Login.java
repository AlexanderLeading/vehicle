package com.alexander.v.tbox.model;

import java.util.List;

public class Login {
	/**
	 * 采集时间
	 */
	private Long collectTime;

	/**
	 * 车载终端每登入一次，登入流水号自动加1，从1开始循环累加，最大值为65531，循环周期为天。
	 */
	private int serialNumber;

	/**
	 * SIM卡ICCID号（ICCID应为终端从SIM卡获取的值，不应人为填写或修改）。
	 */
	private String iccid;

	/**
	 * 可充电储能子系统数n，有效值范围：0～250。
	 */
	private int chargingSystemNumber;

	/**
	 * 可充电储能系统编码长度m，有效范围：0～50，“0”表示不上传该编码。
	 */
	private int chargingSystemLength;

	/**
	 * 可充电储能系统编码宜为终端从车辆获取的值。
	 */
	private List<String> chargingSystemCode;

	public Long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Long collectTime) {
		this.collectTime = collectTime;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public int getChargingSystemNumber() {
		return chargingSystemNumber;
	}

	public void setChargingSystemNumber(int chargingSystemNumber) {
		this.chargingSystemNumber = chargingSystemNumber;
	}

	public int getChargingSystemLength() {
		return chargingSystemLength;
	}

	public void setChargingSystemLength(int chargingSystemLength) {
		this.chargingSystemLength = chargingSystemLength;
	}

	public List<String> getChargingSystemCode() {
		return chargingSystemCode;
	}

	public void setChargingSystemCode(List<String> chargingSystemCode) {
		this.chargingSystemCode = chargingSystemCode;
	}
}
