package com.brt.xwl.rest.controller.redisController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brt.xwl.core.service.redisRelative.RedisService;
import com.brt.xwl.rest.viewObject.ResEntity;

@RestController
@Api(tags="RedisStringController-redis字符串测试接口")
public class RedisStringController {
	
	@Autowired
	RedisService redisService;
	
	@GetMapping("/key/{key}/valuet")
	@ApiOperation(value="string设值")
	@ApiImplicitParam(paramType="path",name="key",value="键",dataType="Long",required=true)
	public ResEntity setString(@PathVariable Long key) {
		String value = "3";
		boolean res = redisService.setString(String.valueOf(key), value);
		if(res) {
			return new ResEntity(null,true,"操作成功");
		}
		return new ResEntity(null,false,"操作失败");
	}
	
	@GetMapping("/key/{key}/value")
	@ApiOperation(value="获得string值")
	@ApiImplicitParam(paramType="path",name="key",value="键",dataType="Long",required=true)
	public ResEntity getString(@PathVariable String key) {
		String res = redisService.getString(key);
		return new ResEntity(res,true,"操作失败");
	}
	
	@GetMapping("/name")
	public String getName() {
		return "name";
	}

}
