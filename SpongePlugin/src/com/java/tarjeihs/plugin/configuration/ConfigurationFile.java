package com.java.tarjeihs.plugin.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationFile {
	private File file;
	private final HashMap<String, ?> configurationMap;
	private final YamlConfiguration config;

	public ConfigurationFile(String filePath, HashMap<String, ?> configurationMap) {
		this.configurationMap = configurationMap;
		if (this.file == null) {
			this.file = new File(filePath);
		}
		this.config = new YamlConfiguration();
		
		try {
			if (!this.file.exists()) {
				for (Map.Entry<String, ?> e : configurationMap.entrySet()) {
					this.config.set(e.getKey(), e.getValue());
				}
				this.config.save(this.file);
			} else {
				this.config.load(this.file);
			}
		} catch (IOException | InvalidConfigurationException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public final YamlConfiguration getConfig() {
		return this.config;
	}

	public File getFile() {
		return this.file;
	}

	public HashMap<String, ?> getConfigurationMap() {
		return this.configurationMap;
	}
}
