package com.spencer.chang.example;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.spencer.chang.rm.RoomMortgage;
import com.spencer.chang.rm.RoomMortgageCashflow;
import com.spencer.chang.rmc.EqualPrincipalCalc;
import com.spencer.chang.rmc.EqualPrincipalInterestCalc;

public class Test {

	public static void main(String[] args) {
		EqualPrincipalCalc epc = new EqualPrincipalCalc();
		EqualPrincipalInterestCalc epic = new EqualPrincipalInterestCalc();
		// 抵押贷款本金39万，利率5.5256%，利率折扣无，利率上浮无，贷款年限30年
		RoomMortgage rm = new RoomMortgage(new BigDecimal(390000),
				new BigDecimal(5.5256).setScale(4, BigDecimal.ROUND_DOWN), null, null, 30);
		ArrayList<RoomMortgageCashflow> result = new ArrayList<RoomMortgageCashflow>();
		// 等额本金
		// result = epc.getEqualPrincipal(rm);
		// for (RoomMortgageCashflow roomMortgageCashflow : result) {
		// System.out.println(roomMortgageCashflow.toString());
		// }
		// 等额本息
		result = epic.getEqualPrincipal(rm);
		for (RoomMortgageCashflow roomMortgageCashflow : result) {
			System.out.println(roomMortgageCashflow.toString());
		}
	}

}
