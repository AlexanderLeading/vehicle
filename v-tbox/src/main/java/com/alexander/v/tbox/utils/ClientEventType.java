package com.alexander.v.tbox.utils;

public enum ClientEventType {

	PLATFORM_LOGIN_SUCCESS("平台登录成功", 1), PLATFORM_LONGIN_FAILE("平台登录失败", 2), VEHICLE_LOGIN_SUCCESS(
			"车辆登录成功", 3), VEHICLE_LONGIN_FAILE("车辆登录失败", 4), REALTIME_SUCCESS(
			"实时消息上报成功", 5), REALTIME_FAILE("实时消息上报失败", 6), RESEND_SUCCESS(
			"补发消息上报成功", 7), RESEND_FAILE("补发消息上报失败", 8), PLATFORM_LOGOUT_SUCCESS(
			"平台登出成功", 9), PLATFORM_LOGOUT_FAILE("平台登出失败", 10), VEHICLE_LOGOUT_SUCCESS(
			"车辆登出成功", 11), VEHICLE_LOGOUT_FAILE("车辆登出失败", 12), VIN_REPEAT(
			"VIN重复", 13), READER_IDLE("客户端读空闲", 14), WRITER_IDLE("客户端写空闲", 15), BCC_CODE_ERROR(
			"BCC校验位错误", 16);

	private String key;
	private int value;

	private ClientEventType(String key, int value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
