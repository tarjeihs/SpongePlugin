package com.java.tarjeihs.plugin.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAnnotation {
	
	public static final String DEFAULT_MESSAGE = "I/T";
	
	public static final int DEFAULT_INTEGER = 1;
	

	String 		command() 		default DEFAULT_MESSAGE;

	String[] 	aliases() 		default { DEFAULT_MESSAGE };

	String 		description() 	default DEFAULT_MESSAGE;

	int 		rankRequired() 	default 1;

	boolean 	use() 			default true;
}
