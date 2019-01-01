package io.github.oguzhancevik.technicalservice.resource;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author oguzhan
 */
public class ExternalResource {

	private Properties properties;

	private static ExternalResource instance;

	public static ExternalResource getInstance(String propName) {
		instance = new ExternalResource(propName);
		return instance;
	}

	public static void setInstance(ExternalResource instance) {
		ExternalResource.instance = instance;
	}

	/**
	 * Bu fonksiyon {@link io.github.oguzhancevik.technicalservice.resource} adresindeki ilgili
	 * properties dosyasını okur.
	 * 
	 * @param propName
	 *            okunacak propertis dosyasının ismi
	 */
	public ExternalResource(String propName) {
		try {
			properties = new Properties();
			InputStream stream = ExternalResource.class.getResourceAsStream(propName + ".properties");
			properties.load(stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param key
	 *            properties dosyasındaki key
	 * @return properties dosyasındaki key'in değerini döndürür.
	 */
	public String getProperty(String key) {
		if (properties == null) {
			return "";
		}
		String value = properties.getProperty(key);
		if (value == null) {
			return "";
		}
		return value;
	}

}
