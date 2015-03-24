package com.java.tarjeihs.plugin;

public enum BlockID {
	
	STONE(1, "stone", (byte) 0),
	GRANITE(1, "granite", (byte) 0),
	POLISHED_GRANITE(1, "polished_granite", (byte) 1),
	DIORITE(1, "diorite", (byte) 2),
	POLISHED_DIORITE(1, "polished_diorite", (byte) 3),
	GRASS(2, "grass", (byte) 0),
	DIRT(3, "dirt", (byte) 0),
	COBBLESTONE(4, "cobblestone", (byte) 0);
	
	private String name;
	private int id;
	private byte data;
	
	private BlockID(int id, String name, byte data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public byte getData() {
		return data;
	}
}
