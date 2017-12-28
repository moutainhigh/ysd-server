package com.qmd.util.bean;

/**
 * 合同参数--月标--汽车--抵押物
 */
public class ContractMoonCarPledge extends ContractPledgeBase{

	// 抵押人/出质人
	private String pledgor;
	// 借款用途
	private String purpose;
	// 车辆牌照号
	private String carLicense;
	// 轿车车型
	private String carType;
	// 厂牌型号
	private String carModel;
	// 发动机号码
	private String carMotor;
	// 车架号/车辆识别号码
	private String carFrame;
	// 漆型/颜色
	private String carColor;
	// 注册日期
	private String carRegister;
	// 质押物价值
	private String carMoney;

	public String getPledgor() {
		return pledgor;
	}

	public void setPledgor(String pledgor) {
		this.pledgor = pledgor;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarMotor() {
		return carMotor;
	}

	public void setCarMotor(String carMotor) {
		this.carMotor = carMotor;
	}

	public String getCarFrame() {
		return carFrame;
	}

	public void setCarFrame(String carFrame) {
		this.carFrame = carFrame;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarRegister() {
		return carRegister;
	}

	public void setCarRegister(String carRegister) {
		this.carRegister = carRegister;
	}

	public String getCarMoney() {
		return carMoney;
	}

	public void setCarMoney(String carMoney) {
		this.carMoney = carMoney;
	}

}
