package com.alexander.v.tbox.netty;

public class ClientConfig {

	public ClientConfig(String ip, int port, String loginName, String loginPass) {
		this.ip = ip;
		this.port = port;
		this.loginName = loginName;
		this.loginPass = loginPass;
	}

	/**
	 * 平台IP
	 */
	private String ip;

	/**
	 * 平台端口
	 */
	private int port;

	/**
	 * 平台账号
	 */
	private String loginName;

	/**
	 * 平台密码
	 */
	private String loginPass;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
}
