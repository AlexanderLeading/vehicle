package com.alexander.v.tbox.model.enums;

public enum ChargeStatus {
	/**
	 * 车辆登入
	 */
	STOP("停车充电", 1),
	/**
	 * 实时信息上报
	 */
	RUN("行车充电", 2),
	/**
	 * 停车充电
	 */
	NOT("未充电", 3),

	/**
	 * 充电完成
	 */
	COMPLETE("充电完成", 4);

	ChargeStatus() {

	}

	ChargeStatus(String key, int value) {
		this.k = key;
		this.v = value;
	}

	private String k;

	private int v;

	public ChargeStatus valueOf(int value) {
		switch (value) {
		case 1:
			return STOP;
		case 2:
			return RUN;
		case 3:
			return NOT;
		case 4:
			return COMPLETE;
		default:
			return null;
		}
	}

	public static String getKey(int value) {
		for (ChargeStatus c : ChargeStatus.values()) {
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
