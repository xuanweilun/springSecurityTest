package com.brt.xwl.core.service.redisRelative;

public interface RedisService {

	/**
	 * @author xuanweilun   
	 * @date 2020年1月3日 下午4:11:31 
	 * @Description: 设置字符串类型值
	 */
	boolean setString(String key,String value);
	
	/**
	 * @author xuanweilun   
	 * @date 2020年1月3日 下午4:11:34 
	 * @Description: 获取字符串类型值
	 */
	String getString(String key);
	
}
