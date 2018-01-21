package com.alexander.v.tbox.model.enums;


public enum VehicleStatus {
	/**
	 * 车辆登入
	 */
	RUN("启动状态", 1),
	/**
	 * 实时信息上报
	 */
	STOP("熄火", 2),
	/**
	 * 停车充电
	 */
	OTHER("其他", 3);
	VehicleStatus() {

	}

	VehicleStatus(String key, int value) {
		this.k = key;
		this.v = value;
	}

	private String k;

	private int v;

	public VehicleStatus valueOf(int value) {
		switch (value) {
		case 1:
			return RUN;
		case 2:
			return STOP;
		case 3:
			return OTHER;
		default:
			return null;
		}
	}

	public static String getKey(int value) {
		for (VehicleStatus c : VehicleStatus.values()) {
			if (c.getV() == value) {
				return c.k;
			}
		}
		return null;
	}

	public String getK() {
		return k;
	}

	public int getV() {
		return v;
	}
}
