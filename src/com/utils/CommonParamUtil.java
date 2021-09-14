package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ��ȡcommonParam��¼�Ĺ�������
 * @author a
 *
 */
public class CommonParamUtil {
	/**
	 * ����
	 */
	public static Map<String,String> PARAM_MAP;

	static{
		PARAM_MAP = new ConcurrentHashMap<String,String>();

		try {
			Properties properties = new Properties();
			InputStream in = CommonParamUtil.class.getClassLoader().getResourceAsStream("commonParam.properties");
			properties.load(in);

			for (String key : properties.stringPropertyNames()) {
				PARAM_MAP.put(key, properties.getProperty(key));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
