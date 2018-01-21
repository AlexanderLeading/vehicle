package com.alexander.v.tbox.model;

public class Data {

	/**
	 * 命令单元
	 */
	private CommandTag commandTag;

	/**
	 * 车架号
	 */
	private String vin;

	/**
	 * 协议的内容
	 */
	private String content;

	public CommandTag getCommandTag() {
		return commandTag;
	}

	public void setCommandTag(CommandTag commandTag) {
		this.commandTag = commandTag;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
