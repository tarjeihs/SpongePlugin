package com.java.tarjeihs.plugin.economy;

import java.util.UUID;

public class Account extends BankManager {

	public Account(UUID userID) {
		super(userID);
	}

	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
