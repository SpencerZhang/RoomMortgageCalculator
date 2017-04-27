# RMC - 房屋抵押贷款计算器

## 说明 / Instructions

RMC - 房屋抵押贷款计算器[等额本金/等额本息]，目前实现计算并生成excel文件

## 例子 / example

```java
package com.spencer.chang.example;

import java.math.BigDecimal;
import java.util.ArrayList;

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
		ArrayList<RoomMortgageCashflow> epcResult = new ArrayList<RoomMortgageCashflow>();
		ArrayList<RoomMortgageCashflow> epicResult = new ArrayList<RoomMortgageCashflow>();
		Excel excel = new Excel();
		// 等额本金
		epcResult = epc.getEqualPrincipal(rm);
		String pathName = "./";
		String epcExeclName = "等额本金.xls";
		excel.exportExcel(epcResult, pathName, epcExeclName);

		// 等额本息
		epicResult = epic.getEqualPrincipal(rm);
		String epicExeclName = "等额本息.xls";
		excel.exportExcel(epicResult, pathName, epicExeclName);
	}
}
```

## 捐助

<p align="center">
<img src="./resource/IMG_0783.JPG" alt="WeChat" title="WeChat" width="200"/>
</p>
<p align="center">
<img src="./resource/IMG_0784.JPG" alt="AliPay" title="AliPay" width="200"/>
</p>


## 开源协议 / License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2017 Spencer.Chang. All rights reserved.

