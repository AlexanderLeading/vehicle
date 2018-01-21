package com.alexander.v.tbox.model;

import java.util.ArrayList;
import java.util.List;

public class DriveMotor {

	/**
	 * 驱动电机总个数
	 */
	private int number;

	private List<DriveMotorItem> itemList = new ArrayList<DriveMotorItem>();

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<DriveMotorItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<DriveMotorItem> itemList) {
		this.itemList = itemList;
	}
}
