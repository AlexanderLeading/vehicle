package com.alexander.v.tbox.model.enums;

public enum DcStatus {
	/**
	 * 车辆登入
	 */
	EV("纯电", 1),
	/**
	 * 实时信息上报
	 */
	HV("混动", 2),
	/**
	 * 停车充电
	 */
	FV("燃油", 3);
	DcStatus() {

	}

	DcStatus(String key, int value) {
		this.k = key;
		this.v = value;
	}

	private String k;

	private int v;

	public DcStatus valueOf(int value) {
		switch (value) {
		case 1:
			return EV;
		case 2:
			return HV;
		case 3:
			return FV;
		default:
			return null;
		}
	}

	public static String getKey(int value) {
		for (DcStatus c : DcStatus.values()) {
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
