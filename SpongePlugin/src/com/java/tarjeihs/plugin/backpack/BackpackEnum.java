package com.java.tarjeihs.plugin.backpack;

public enum BackpackEnum {

	DIAMOND_BACKPACK(1),
	LEATHER_BACKPACK(2);
	
	private int data;
	
	BackpackEnum(int data) {
		this.data = data;
	}
	
	public int getData() {
		return data;
	}
}
