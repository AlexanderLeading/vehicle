package com.alexander.v.gateway.model;


public enum CommandTag {

	/* 上行命令 */
	/**
	 * 车辆登入
	 */
	VEHICLE_LOGIN("车辆登入", 1),
	/**
	 * 实时信息上报
	 */
	REALTIME_INFO_REPORT("实时信息上报", 2),
	/**
	 * 停车充电
	 */
	RESEND_INFO_REPORT("补发信息上报", 3),
	/**
	 * 车辆登出
	 */
	VEHICLE_LOOUT("车辆登出", 4),
	/**
	 * 平台登入
	 */
	PLATFORM_LOGIN("平台登入", 5),
	/**
	 * 平台登出
	 */
	PLATFORM_LOOUT("平台登出", 6),
	/**
	 * 心跳
	 */
	HEARTBEAT("心跳", 7);

	CommandTag() {

	}

	CommandTag(String key, int value) {
		this.k = key;
		this.v = value;
	}

	private String k;

	private int v;

	public CommandTag valueOf(byte value) {
		switch (value) {
		case 0x01:
			return VEHICLE_LOGIN;
		case 0x02:
			return REALTIME_INFO_REPORT;
		case 0x03:
			return RESEND_INFO_REPORT;
		case 0x04:
			return VEHICLE_LOOUT;
		case 0x05:
			return PLATFORM_LOGIN;
		case 0x06:
			return PLATFORM_LOOUT;
		case 0x07:
			return HEARTBEAT;

		default:
			return null;
		}
	}

	public static CommandTag valOf(byte value) {
		switch (value) {
		case 0x01:
			return VEHICLE_LOGIN;
		case 0x02:
			return REALTIME_INFO_REPORT;
		case 0x03:
			return RESEND_INFO_REPORT;
		case 0x04:
			return VEHICLE_LOOUT;
		case 0x05:
			return PLATFORM_LOGIN;
		case 0x06:
			return PLATFORM_LOOUT;
		case 0x07:
			return HEARTBEAT;
		default:
			return null;
		}
	}

	public static String getKey(int value) {
		for (CommandTag c : CommandTag.values()) {
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
