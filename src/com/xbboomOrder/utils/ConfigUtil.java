package com.xbboomOrder.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 閸掓稑缂撻弮鍫曟？閿涳拷2016楠烇拷6閺堬拷14閺冿拷 娑撳宕�7:47:27
 * 
 * @author andy
 * @version 2.2
 */

public class ConfigUtil {

	private static final Logger LOG = Logger.getLogger(ConfigUtil.class);

	private static Properties config = null;

	/**
	 * 鏉╂柨娲栫化鑽ょ埠config.properties闁板秶鐤嗘穱鈩冧紖
	 * @param key key閸婏拷
	 * @return value閸婏拷
	 */
	public static String getProperty(String key) {
		System.out.println();
		if (config == null) {
			synchronized (ConfigUtil.class) {
				if (null == config) {
					try {
						Resource resource = new ClassPathResource("config.properties");
						config = PropertiesLoaderUtils.loadProperties(resource);
					} catch (IOException e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		}

		return config.getProperty(key);
	}
}
