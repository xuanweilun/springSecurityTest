package com.brt.xwl.core.service.redisRelative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.brt.xwl.core.utils.RedisUtils;

@Service
@EnableScheduling
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	RedisUtils redisUtils;

	@Override
	public boolean setString(String key, String value) {
		boolean res = redisUtils.set(key, value);
		return res;
	}

	@Override
	public String getString(String key) {
		String value = (String) redisUtils.get(key);
		return value;
	}
	
	@Scheduled(cron = "0 0 0 1 * ? ")
	public void sys() {
		System.out.println("xuanweilun haha .");
	}

}
