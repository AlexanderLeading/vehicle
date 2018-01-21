package com.alexander.v.tbox.model.enums;

public enum Dw {
	G0("空挡", 0), G1("1档", 1), G2("2档", 2), G3("3档", 3), G4("4档", 4), G5("5档", 5), G6(
			"6档", 6), GR("倒档", 7), GD("自动D档", 8), GP("停车P档", 9);

	Dw() {

	}

	Dw(String key, int value) {
		this.k = key;
		this.v = value;
	}

	private String k;

	private int v;

	public Dw valueOf(int value) {
		switch (value) {
		case 0:
			return G0;
		case 1:
			return G1;
		case 2:
			return G2;
		case 3:
			return G3;
		case 4:
			return G4;
		case 5:
			return G5;
		case 6:
			return G6;
		case 7:
			return GR;
		case 8:
			return GD;
		case 9:
			return GP;
		default:
			return null;
		}
	}

	public static String getKey(int value) {
		for (Dw c : Dw.values()) {
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
