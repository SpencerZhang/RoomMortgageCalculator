package com.spencer.chang.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import com.spencer.chang.excel.Excel;
import com.spencer.chang.rm.RoomMortgage;
import com.spencer.chang.rm.RoomMortgageCashflow;
import com.spencer.chang.rmc.EqualPrincipalCalc;
import com.spencer.chang.rmc.EqualPrincipalInterestCalc;

public class ExcelTest {
	public static void main(String[] args) {
		EqualPrincipalCalc epc = new EqualPrincipalCalc();
		EqualPrincipalInterestCalc epic = new EqualPrincipalInterestCalc();
		
		// 抵押贷款本金39万，利率5.5256%，利率折扣无，利率上浮无，贷款年限30年
		// 当从前端传入的利率或者利率折扣或者利率上浮出入的是Double，初始化BigDecimal,值会不等传入的值。
		// 这是应该使用String类型，初始化BigDecimal
		// 例如：
		// BigDecimal bd2 = new BigDecimal(5.5256);
		// System.out.println(bd2);
		// BigDecimal bd3 = new BigDecimal("5.5256");
		// System.out.println(bd3);
		// 打印结果
		// 5.52559999999999984510168360429815948009490966796875
		// 5.5256
		// 传入的参数有关金额字段定义为String类型，全部使用BigDecimal(String)初始化。保证精确计算
		RoomMortgage rm = new RoomMortgage(new BigDecimal("390000"),
				new BigDecimal("5.5256").setScale(4, BigDecimal.ROUND_DOWN), null, null, 30);
		
		//为了规避NullPointerExceptions，使用jdk 8 新特性 Optional
		ArrayList<Optional<RoomMortgageCashflow>> epcResult = new ArrayList<Optional<RoomMortgageCashflow>>();
		ArrayList<Optional<RoomMortgageCashflow>> epicResult = new ArrayList<Optional<RoomMortgageCashflow>>();
		Excel excel = new Excel();
		
		// 等额本金
		epcResult = epc.getEqualPrincipal(rm);
		String pathName = "./";
		String epcExeclName = "等额本金.xls";
		excel.exportExcel(epcResult, pathName, epcExeclName);

		// 等额本息
		epicResult = epic.getEqualPrincipalInterest(rm);
		String epicExeclName = "等额本息.xls";
		excel.exportExcel(epicResult, pathName, epicExeclName);
	}
}
