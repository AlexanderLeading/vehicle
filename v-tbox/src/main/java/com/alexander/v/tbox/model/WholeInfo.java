package com.alexander.v.tbox.model;

import com.accp.v.tbox.model.enums.ChargeStatus;
import com.accp.v.tbox.model.enums.DcStatus;
import com.accp.v.tbox.model.enums.Gear;
import com.accp.v.tbox.model.enums.RunModel;
import com.accp.v.tbox.model.enums.VehicleStatus;

public class WholeInfo {
	/**
	 * 车辆状态
	 */
	private VehicleStatus vehicleStatus;

	/**
	 * 充电状态
	 */
	private ChargeStatus chargeStatus;

	/**
	 * 运行模式
	 */
	private RunModel runModel;

	/**
	 * 车速
	 */
	private double speed;

	/**
	 * 累计里程
	 */
	private double odo;

	/**
	 * 总电压
	 */
	private double voltage;

	/**
	 * 总电流
	 */
	private double current;

	/**
	 * 剩余电量
	 */
	private int soc;

	/**
	 * DC-DC状态
	 */
	private DcStatus dcStatus;

	/**
	 * 档位
	 */
	private Gear gear;
	
	/**
	 * 绝缘电阻
	 */
	private double inResistance;

	public VehicleStatus getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public ChargeStatus getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(ChargeStatus chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public RunModel getRunModel() {
		return runModel;
	}

	public void setRunModel(RunModel runModel) {
		this.runModel = runModel;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getOdo() {
		return odo;
	}

	public void setOdo(double odo) {
		this.odo = odo;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public int getSoc() {
		return soc;
	}

	public void setSoc(int soc) {
		this.soc = soc;
	}

	public DcStatus getDcStatus() {
		return dcStatus;
	}

	public void setDcStatus(DcStatus dcStatus) {
		this.dcStatus = dcStatus;
	}

	public Gear getGear() {
		return gear;
	}

	public void setGear(Gear gear) {
		this.gear = gear;
	}

	public double getInResistance() {
		return inResistance;
	}

	public void setInResistance(double inResistance) {
		this.inResistance = inResistance;
	} 
}
