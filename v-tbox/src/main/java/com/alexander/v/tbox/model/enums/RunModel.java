package com.alexander.v.tbox.model.enums;

public enum RunModel {
	/**
	 * 车辆登入
	 */
	RUN("工作", 1),
	/**
	 * 实时信息上报
	 */
	STOP("断开", 2);

	RunModel() {

	}

	RunModel(String key, int value) {
		this.k = key;
		this.v = value;
	}

	private String k;

	private int v;

	public RunModel valueOf(int value) {
		switch (value) {
		case 1:
			return RUN;
		case 2:
			return STOP;
		default:
			return null;
		}
	}

	public static String getKey(int value) {
		for (RunModel c : RunModel.values()) {
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
