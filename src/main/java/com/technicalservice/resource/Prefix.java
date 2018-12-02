package com.technicalservice.resource;

import com.technicalservice.util.UtilLog;

public class Prefix {

	private static String pathBegin;

	/**
	 * @return sisteme yüklenen belgelerin(resim,doküman) işletim sistemine göre
	 *         tutulduğu adresi döner.
	 */
	public static String getPathBegin() {
		if (pathBegin == null) {
			try {
				String osName = System.getProperty("os.name");
				UtilLog.log("PREFIX: OperatingSystem:" + osName);
				if (osName.startsWith("Windows")) {
					pathBegin = "C:/technicalservice/";
				} else if (osName.startsWith("Linux")) {
					pathBegin = "/home/technicalservice/";
				} else if (osName.startsWith("Mac")) {
					pathBegin = "/Users/technicalservice/";
				} else {
					throw new Exception("PREFIX: Unknown OS");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return pathBegin;
	}

	public static void setPathBegin(String pathBegin) {
		Prefix.pathBegin = pathBegin;
	}

}
