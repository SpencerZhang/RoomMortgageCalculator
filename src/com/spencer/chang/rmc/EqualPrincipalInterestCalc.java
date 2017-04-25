package com.spencer.chang.rmc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import com.spencer.chang.rm.RoomMortgage;
import com.spencer.chang.rm.RoomMortgageCashflow;

/**
 * 等额本息计算器
 * 
 */
public class EqualPrincipalInterestCalc {
	private static final BigDecimal RATE_DIVISOR = new BigDecimal("100");
	private static final Integer month = 12;

	/**
	 * 获取计划还款现金流
	 * 
	 * @param rm
	 *            抵押贷款信息
	 * @return 计划还款现金流对象数组
	 */
	public ArrayList<RoomMortgageCashflow> getEqualPrincipal(RoomMortgage rm) {
		// 抵押贷款总额
		BigDecimal mortgagePrincipal = rm.getMortgagePrincipal();
		// 根据还款年数*12,求还款总月数
		int totalMonth = rm.getYear() * month;
		// 贷款利率
		BigDecimal rate = rm.getRate();
		// 年利率折扣
		BigDecimal discountRate = rm.getDiscountRate();
		// 年利率上浮
		BigDecimal floatRate = rm.getFloatRate();
		ArrayList<RoomMortgageCashflow> al = calc(mortgagePrincipal, totalMonth, rate, discountRate, floatRate);
		return al;
	}

	/**
	 * 计算抵押贷款现金流
	 * 
	 * @param mortgagePrincipal
	 *            抵押贷款总额
	 * @param totalMonth
	 *            抵押贷款月数
	 * @param rate
	 *            贷款利率
	 * @param discountRate
	 *            折扣
	 * @param floatRate
	 *            上浮
	 * @return 抵押贷款现金流数组
	 */
	private ArrayList<RoomMortgageCashflow> calc(BigDecimal mortgagePrincipal, int totalMonth, BigDecimal rate,
			BigDecimal discountRate, BigDecimal floatRate) {
		// 已还本金
		BigDecimal paidPrincipal = BigDecimal.ZERO;
		// 已还利息
		BigDecimal paidInterset = BigDecimal.ZERO;
		// 剩余本金
		BigDecimal remainingPrincipal = mortgagePrincipal.subtract(paidPrincipal);
		// 获取月利率
		// 保留6位小数
		BigDecimal monthRate = getMonthRate(rate, discountRate, floatRate);

		// 每月应还额
		BigDecimal dueMonthAmount = getDueMonthAmount(mortgagePrincipal, totalMonth, monthRate);

		// 每月应还本金
		BigDecimal dueMonthPrincipal = BigDecimal.ZERO;
		// 已还款总额
		BigDecimal paidAmount = BigDecimal.ZERO;

		ArrayList<RoomMortgageCashflow> al = new ArrayList<RoomMortgageCashflow>();
		for (int i = 1; i <= totalMonth; i++) {
			// JDK8 新特性 直接返回 2017-04-24 还款日期
			LocalDate dueDate = LocalDate.now();
			// 每月应还利息
			BigDecimal dueMonthInterset = getDueMonthInterset(mortgagePrincipal, dueMonthAmount, monthRate, i);

			// 最后一个月的本金(倒减法)= 总金额-已还总金额
			if (i == totalMonth)
				dueMonthPrincipal = mortgagePrincipal.subtract(paidPrincipal);

			// 每月应还本金
			dueMonthPrincipal = dueMonthAmount.subtract(dueMonthInterset);
			paidPrincipal = paidPrincipal.add(dueMonthPrincipal);

			// 剩余本金 = 抵押贷款总额 - 已还本金
			remainingPrincipal = mortgagePrincipal.subtract(paidPrincipal);
			// 最后一个月的剩余本金为0
			if (i == totalMonth)
				remainingPrincipal = BigDecimal.ZERO;

			paidInterset = paidInterset.add(dueMonthInterset);

			// 每月还款额,保留2位小数
			dueMonthAmount = dueMonthAmount.setScale(2, BigDecimal.ROUND_DOWN);
			// 已还款总额
			paidAmount = paidAmount.add(dueMonthAmount);

			// 创建计划还款现金流对象
			RoomMortgageCashflow rmc = new RoomMortgageCashflow();
			setRMC(paidPrincipal, paidInterset, remainingPrincipal, dueMonthPrincipal, paidAmount, al, i, dueDate,
					dueMonthInterset, dueMonthAmount, rmc);
		}
		return al;
	}

	/**
	 * set 计划还款现金流对象
	 * 
	 * @param paidPrincipal
	 * @param paidInterset
	 * @param remainingPrincipal
	 * @param dueMonthPrincipal
	 * @param paidAmount
	 * @param al
	 * @param i
	 * @param dueDate
	 * @param dueMonthInterset
	 * @param dueMonthAmount
	 * @param rmc
	 */
	private void setRMC(BigDecimal paidPrincipal, BigDecimal paidInterset, BigDecimal remainingPrincipal,
			BigDecimal dueMonthPrincipal, BigDecimal paidAmount, ArrayList<RoomMortgageCashflow> al, int i,
			LocalDate dueDate, BigDecimal dueMonthInterset, BigDecimal dueMonthAmount, RoomMortgageCashflow rmc) {
		if (i == 1)
			rmc.setDueDate(dueDate);
		else
			rmc.setDueDate(dueDate.plusMonths(i - 1));
		rmc.setDueMonthAmount(dueMonthAmount.setScale(2, BigDecimal.ROUND_DOWN));
		rmc.setDueMonthInterset(dueMonthInterset.setScale(2, BigDecimal.ROUND_DOWN));
		rmc.setDueMonthPrincipal(dueMonthPrincipal.setScale(2, BigDecimal.ROUND_DOWN));
		rmc.setPaidInterset(paidInterset.setScale(2, BigDecimal.ROUND_DOWN));
		rmc.setPaidPrincipal(paidPrincipal.setScale(2, BigDecimal.ROUND_DOWN));
		rmc.setRemainingPrincipal(remainingPrincipal.setScale(2, BigDecimal.ROUND_DOWN));
		rmc.setPaidAmount(paidAmount.setScale(2, BigDecimal.ROUND_DOWN));
		al.add(rmc);
	}

	/**
	 * 获取月利率,保留6位小数
	 * 
	 * @param rate
	 *            贷款利率
	 * @param discountRate
	 *            折扣
	 * @param floatRate
	 *            上浮
	 * @return monthRate
	 */
	private BigDecimal getMonthRate(BigDecimal rate, BigDecimal discountRate, BigDecimal floatRate) {
		BigDecimal yearRate = rate.divide(RATE_DIVISOR);
		// 获取月利率
		// 保留6位小数
		BigDecimal monthRate = yearRate.divide(new BigDecimal(month), 6, BigDecimal.ROUND_DOWN);

		if (discountRate != null)
			monthRate = monthRate.subtract(discountRate.divide(RATE_DIVISOR));
		else if (floatRate != null)
			monthRate = monthRate.add(floatRate.divide(RATE_DIVISOR));
		return monthRate;
	}

	/**
	 * * 等额本息计算获取还款本息和
	 * 
	 * 每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
	 * 
	 * @param mortgagePrincipal
	 *            贷款本金
	 * @param totalMonth
	 *            贷款月数
	 * @param monthRate
	 *            月利率
	 * @return dueMonthAmount 每月还款本息和
	 */
	private static BigDecimal getDueMonthAmount(BigDecimal mortgagePrincipal, int totalMonth, BigDecimal monthRate) {
		// (1＋月利率)＾还款月数
		BigDecimal ratePow = monthRate.add(new BigDecimal("1")).pow(totalMonth);
		// (1＋月利率)＾还款月数-1
		BigDecimal ratePowSub = ratePow.subtract(new BigDecimal("1"));
		BigDecimal dueMonthAmount = mortgagePrincipal.multiply(monthRate).multiply(ratePow).divide(ratePowSub, 2,
				BigDecimal.ROUND_DOWN);
		return dueMonthAmount;
	}

	/**
	 * 等额本息计算获取还款方式为等额本息的每月偿还利息
	 * 
	 * 每月应还利息=(贷款本金x月利率-每月还款本息和)×[(1+月利率)^ 还款月份-1]+每月还款本息和
	 * 
	 * @param mortgagePrincipal
	 *            贷款本金
	 * @param monthRate
	 *            月利率
	 * @param dueMonthAmount
	 *            每月还款本息和
	 * @param dueMonth
	 *            还款月份/第n期
	 * @return dueMonthInvest 每月偿还利息
	 */
	private static BigDecimal getDueMonthInterset(BigDecimal mortgagePrincipal, BigDecimal dueMonthAmount,
			BigDecimal monthRate, int dueMonth) {
		// (1＋月利率)＾还款月数
		BigDecimal ratePow = monthRate.add(new BigDecimal("1")).pow(dueMonth);
		// (1＋月利率)＾还款月份-1
		BigDecimal ratePowSub = ratePow.subtract(new BigDecimal("1"));
		BigDecimal dueMonthInvest = mortgagePrincipal.multiply(monthRate).subtract(dueMonthAmount).multiply(ratePowSub)
				.add(dueMonthAmount);
		return dueMonthInvest;
	}
}
