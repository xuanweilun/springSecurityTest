package com.brt.xwl.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.brt.xwl.rest.viewObject.ResEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

@RestController
@Api(tags="HelloBitch")
public class HelloBitch {

	@GetMapping("/person/{personId}/name")
	@ApiOperation(value="查询用户名接口")
	@ApiImplicitParam(paramType="path",name="userId",value="用户id",dataType="Long",required=true)
	public ResEntity getNames(@PathVariable Long userId) {
		return new ResEntity("张三",true,"成功");
	}
}
