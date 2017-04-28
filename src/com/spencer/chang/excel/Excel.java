package com.spencer.chang.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.spencer.chang.rm.RoomMortgageCashflow;

/**
 * 生成excel文件
 * 
 * @author Spencer
 *
 */
public class Excel {
	public void exportExcel(ArrayList<Optional<RoomMortgageCashflow>> result, String pathName, String excelName) {
		// 创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 在webbook中添加一个sheet
		HSSFSheet sheet = wb.createSheet("还款现金流");
		// 在sheet中添加表头第0行
		HSSFRow row = sheet.createRow(0);
		// 创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 居中格式
		style.setAlignment(HorizontalAlignment.CENTER);

		// 设置列名
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("还款日");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("应还金额");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("应还本金");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("应还利息");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("已还本金");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("已还利息");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("剩余应还本金");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("已还金额");
		cell.setCellStyle(style);

		// 创建行列，并赋值
		for (int i = 0; i < result.size(); i++) {
			//jdk 8 新特性 Optional
			Optional<RoomMortgageCashflow> ormc = result.get(i);
			//检查Optional类型对象是否有值
			if (ormc.isPresent()) {
				//获取对象
				RoomMortgageCashflow rmc = ormc.get();
				
				row = sheet.createRow(i + 1);
				// 创建单元格，并设置值
				row.createCell(0).setCellValue(rmc.getDueDate());
				row.createCell(1).setCellValue(rmc.getDueMonthAmount().doubleValue());
				row.createCell(2).setCellValue(rmc.getDueMonthPrincipal().doubleValue());
				row.createCell(3).setCellValue(rmc.getDueMonthInterset().doubleValue());
				row.createCell(4).setCellValue(rmc.getPaidPrincipal().doubleValue());
				row.createCell(5).setCellValue(rmc.getPaidInterset().doubleValue());
				row.createCell(6).setCellValue(rmc.getRemainingPrincipal().doubleValue());
				row.createCell(7).setCellValue(rmc.getPaidAmount().doubleValue());
			}
		}

		// 生成excel文件
		try {
			FileOutputStream out = new FileOutputStream(pathName + excelName);
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
