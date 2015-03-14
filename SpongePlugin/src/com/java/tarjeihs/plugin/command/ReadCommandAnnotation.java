package com.java.tarjeihs.plugin.command;

import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class ReadCommandAnnotation {

	private final Class<?> clazz;
	private final String DEFAULT_ERROR = "ERROR";
	private final int DEFAULT_INTEGER = 1;
	
	private String command;
	private String description;
	private String[] aliases;

	private boolean useable;
	private int rank;

	public ReadCommandAnnotation(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void register() {
		Method[] methods = this.clazz.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(CommandAnnotation.class)) {
				CommandAnnotation commandAnnotation = (CommandAnnotation) method.getAnnotation(CommandAnnotation.class);
				this.command = commandAnnotation.command();
				this.description = commandAnnotation.description();
				this.useable = commandAnnotation.use();
				this.aliases = commandAnnotation.aliases();
				this.rank = commandAnnotation.rankRequired();
			}
		}
	}

	public String[] getAliases() {
		if (this.aliases != null) {
			return this.aliases;
		}
		return new String[] { "ERROR" };
	}

	public String getCommand() {
		if (this.command != null) {
			return this.command;
		}
		return "ERROR";
	}

	public String getDescription() {
		if (this.description != null) {
			return this.description;
		}
		return "ERROR";
	}

	public boolean isUseable() {
		if (this.useable) {
			return true;
		}
		return false;
	}

	public int getRank() {
		if (this.rank >= 1) {
			return this.rank;
		}
		return 1;
	}
}
