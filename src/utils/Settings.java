package utils;


import exception.PropertyIsNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static final String PATH = "src/resources/settings.properties";

	private static Settings instance;

	private Properties properties;

	private Settings() {
		properties = new Properties();
	}

	private void load(String path) {
		try {
			properties.load(new FileInputStream(new File(path)));
		} catch (IOException e) {

		}
	}

	public String get(String key) {
		String value = properties.getProperty(key);

		if (value == null) {
			throw new PropertyIsNotFoundException();
		}

		return value;
	}

	public int getInt(String key) {
		String string = get(key);
		return Integer.parseInt(string);
	}

	public double getDouble(String key) {
		String string = get(key);
		return Double.parseDouble(string);
	}

	private static void init() {
		if (instance == null) {
			instance = new Settings();
			instance.load(PATH);
		}
	}

	public static Settings getInstance() {
		init();
		return instance;
	}
}
