package com.zuipin.interview.yeyinghao.controller;

import cn.hutool.core.util.StrUtil;
import com.zuipin.interview.yeyinghao.common.Response;
import com.zuipin.interview.yeyinghao.controller.result.CalculateResult;
import com.zuipin.interview.yeyinghao.exception.ServiceException;
import com.zuipin.interview.yeyinghao.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计算并返回两个时间段的交集和剩余时间段 控制器
 *
 * @author yeyinghao
 */
@RestController
public class CalculateController {

	@Autowired
	private CalculateService calculateService;

	@GetMapping("/calculate")
	public Response<CalculateResult> getCalculateResult(String tp1, String tp2) {
		if (StrUtil.isEmpty(tp1) || StrUtil.isEmpty(tp2)) {
			throw new ServiceException("必须需要传入两个时间段数据,才能进行计算");
		}
		return Response.ok(calculateService.getCalculateResult(tp1, tp2));
	}

}
