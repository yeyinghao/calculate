package com.zuipin.interview.yeyinghao.controller.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算并返回两个时间段的交集和剩余时间段 返回参数对象
 *
 * @author yeyinghao
 */
public class CalculateResult {

	/**
	 * "leftRemain"   : []
	 * "intersection" : "08:00 10:00",
	 * "rightRemain"  : [ "05:00 ~ 08:00", "10:00 ~ 18:00"]
	 */

	private List<String> leftRemain = new ArrayList<>();

	private String intersection = "";

	private List<String> rightRemain = new ArrayList<>();

	public CalculateResult(List<String> leftRemain, String intersection, List<String> rightRemain) {
		this.leftRemain = leftRemain;
		this.intersection = intersection;
		this.rightRemain = rightRemain;
	}

	public CalculateResult() {
	}

	public List<String> getLeftRemain() {
		return leftRemain;
	}

	public void setLeftRemain(List<String> leftRemain) {
		this.leftRemain = leftRemain;
	}

	public String getIntersection() {
		return intersection;
	}

	public void setIntersection(String intersection) {
		this.intersection = intersection;
	}

	public List<String> getRightRemain() {
		return rightRemain;
	}

	public void setRightRemain(List<String> rightRemain) {
		this.rightRemain = rightRemain;
	}
}
