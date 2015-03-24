package com.java.tarjeihs.plugin.backpack;

public class Backpack {
	
	private int size;
	private int slots;
	
	public Backpack(int size, int slots) {
		this.size = size;
		this.slots = slots;
	}

	public int getSize() {
		return size;
	}
	
	public int getSlots() {
		return slots;
	}
}
