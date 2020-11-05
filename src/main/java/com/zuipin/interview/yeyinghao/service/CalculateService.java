package com.zuipin.interview.yeyinghao.service;

import com.zuipin.interview.yeyinghao.controller.result.CalculateResult;

/**
 * 计算并返回两个时间段的交集和剩余时间段 服务
 *
 * @author yeyinghao
 */
public interface CalculateService {
	/**
	 * 计算并返回两个时间段的交集和剩余时间段
	 *
	 * @param tp1 时间段1
	 * @param tp2 时间段2
	 * @return CalculateResult
	 */
	CalculateResult getCalculateResult(String tp1, String tp2);
}
