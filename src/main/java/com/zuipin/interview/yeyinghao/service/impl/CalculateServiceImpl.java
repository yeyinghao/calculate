package com.zuipin.interview.yeyinghao.service.impl;

import cn.hutool.core.date.DateUtil;
import com.zuipin.interview.yeyinghao.controller.result.CalculateResult;
import com.zuipin.interview.yeyinghao.exception.ServiceException;
import com.zuipin.interview.yeyinghao.service.CalculateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 计算并返回两个时间段的交集和剩余时间段 业务处理
 *
 * @author yeyinghao
 */
@Service
public class CalculateServiceImpl implements CalculateService {

	private Logger log = LoggerFactory.getLogger(CalculateServiceImpl.class);

	@Override
	public CalculateResult getCalculateResult(String tp1, String tp2) {

		// 解析第一个时间段数据
		Date[] date1s = dealTimeData(tp1, 1);

		// 解析第二个时间段数据
		Date[] date2s = dealTimeData(tp2, 2);

		// 计算结果
		return getResult(date1s, date2s, tp1, tp2);
	}


	/**
	 * 解析类似 08:00~10:00 这种格式的 时间段数据
	 *
	 * @param time 08:00~10:00
	 */
	private Date[] dealTimeData(String time, int i) {

		if (!time.contains("~")) {
			throw new ServiceException("第" + i + "个的时间段格式不正确,缺少~符号");
		}

		String[] hours = time.split("~");

		if (hours.length != 2) {
			throw new ServiceException("第" + i + "个的时间段格式不正确,根据~符号拆分后得到数据不正确");
		}

		String today = DateUtil.today();
		String time1 = today + " " + hours[0];
		String time2 = today + " " + hours[1];

		Date date1;
		Date date2;
		try {
			date1 = DateUtil.parse(time1, "yyyy-MM-dd HH:mm");
			date2 = DateUtil.parse(time2, "yyyy-MM-dd HH:mm");
		} catch (Exception e) {
			throw new ServiceException("第" + i + "个的时间段格式不正确");
		}
		return new Date[]{date1, date2};
	}

	public CalculateResult getResult(Date[] date1s, Date[] date2s, String tp1, String tp2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		SimpleDateFormat sdfTemp = new SimpleDateFormat("HH:mm");

		// 标准时间
		Date bt = date1s[0];
		Date ot = date1s[1];
		// 目标时间
		Date st = date2s[0];
		Date ed = date2s[1];

		// 时间段开始时间
		long leftStart = bt.getTime();
		// 时间段结束时间
		long leftEnd = ot.getTime();
		// 右边开始时间
		long rightStart = st.getTime();
		// 右边结束时间
		long rightEnd = ed.getTime();

		CalculateResult calculateResult = new CalculateResult();

		List<String> left = new ArrayList<>();
		List<String> right = new ArrayList<>();


		// 具体算法如下
		// 首先看是否有包含关系 如果为true 肯定有交集
		if (leftStart <= rightEnd || rightStart <= leftEnd) {
			// 一定有重叠部分
			long sblong = Math.max(leftStart, rightStart);
			long eblong = Math.min(leftEnd, rightEnd);
			log.info("包含的开始时间是：" + sdf.format(sblong) + "-结束时间是：" + sdf.format(eblong));

			if (sblong <= eblong) {
				// 求得时间段交集
				calculateResult.setIntersection(sdfTemp.format(sblong) + "~" + sdfTemp.format(eblong));
			}

			// 求左剩余
			getLeftRemain(sdfTemp, leftStart, leftEnd, left, sblong, eblong);

			// 求右剩余
			getLeftRemain(sdfTemp, rightStart, rightEnd, right, sblong, eblong);
		} else {
			// 两个时间段无任何交集
			calculateResult.setIntersection("");
			left.add(tp1);
			right.add(tp2);
		}
		calculateResult.setLeftRemain(left);
		calculateResult.setRightRemain(right);
		return calculateResult;
	}

	private void getLeftRemain(SimpleDateFormat sdfTemp, long start, long end, List<String> reamins, long sblong, long eblong) {
		// 当时间段的最小大于等于交集的最小值 并且 时间段的最大小于等于交集的最大值 此时 时间段 剩下 无
		if (start >= sblong && end <= eblong) {
		}

		// 当时间段的最小 小于交集的最小值 并且 时间段的最大 大于交集的最大值 此时 时间段 剩下
		if (start < sblong && end > eblong) {
			reamins.add(sdfTemp.format(start) + "~" + sdfTemp.format(sblong));
			reamins.add(sdfTemp.format(eblong) + "~" + sdfTemp.format(end));
		}

		// 当时间段的最小 小于交集的最小值 并且 时间段的最大 小于等于交集的最大值 此时 时间段 剩下
		if (start < sblong && end <= eblong) {
			reamins.add(sdfTemp.format(start) + "~" + sdfTemp.format(sblong));
		}

		// 当时间段的最小 大于等于交集的最小值 并且 时间段的最大 大于交集的最大值 此时 时间段 剩下
		if (start >= sblong && end > eblong) {
			reamins.add(sdfTemp.format(eblong) + "~" + sdfTemp.format(end));
		}
	}
}
