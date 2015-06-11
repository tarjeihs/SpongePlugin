package com.java.tarjeihs.plugin.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.logging.Logger;

public class Util {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(java.lang.Thread.currentThread().getStackTrace()[0].getClassName());
	
	public static void save(Object obj, File directory){
		ObjectOutputStream oops;
		try {
			oops = new ObjectOutputStream(new FileOutputStream(directory));
			oops.writeObject(obj);
			oops.flush();
			oops.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean deleteUnusedFile(File file, int timeLimit) {
		long diff = new Date().getTime() - file.lastModified();

		if (diff > timeLimit * 24 * 60 * 60 * 1000) {
		    file.delete();
		    return true;
		}
		return false;
	}
	
	public static Object load(File directory) {
		ObjectInputStream ois;
		Object result = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(directory));
			result = ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
