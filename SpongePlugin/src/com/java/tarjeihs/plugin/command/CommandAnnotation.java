package com.java.tarjeihs.plugin.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAnnotation {
	
	public static final String DEFAULT_MESSAGE = "ERROR";
	
	public static final int DEFAULT_INTEGER = 1;
	

	String 		command() 		default "ERROR";

	String[] 	aliases() 		default { "ERROR" };

	String 		description() 	default "ERROR";

	int 		rankRequired() 	default 1;

	boolean 	use() 			default true;
}
