package com.alexander.v.tbox.model;

public class RealInfo {
	/**
	 * 采集时间
	 */
	private Long collectTime;

	/**
	 * 整车数据
	 */
	private WholeInfo wholeInfo;

	/**
	 * 驱动电机
	 */
	private DriveMotor driveMotor;

	/**
	 * 车辆位置
	 */
	private GPS gps;

	/**
	 * 极值
	 */
	private Extreme extreme;

	/**
	 * 告警
	 */
	private Alarm alarm;

	public Long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Long collectTime) {
		this.collectTime = collectTime;
	}

	public WholeInfo getWholeInfo() {
		return wholeInfo;
	}

	public void setWholeInfo(WholeInfo wholeInfo) {
		this.wholeInfo = wholeInfo;
	}

	public DriveMotor getDriveMotor() {
		return driveMotor;
	}

	public void setDriveMotor(DriveMotor driveMotor) {
		this.driveMotor = driveMotor;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public Extreme getExtreme() {
		return extreme;
	}

	public void setExtreme(Extreme extreme) {
		this.extreme = extreme;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
}
