package com.spencer.chang.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spencer.chang.rm.RoomMortgageCashflow;

/**
 * 生成excel文件
 * 
 * @author Spencer
 *
 */
public class Excel {

	/**
	 * 导出excel文件
	 * 
	 * @param result
	 * @param pathName
	 * @param excelName
	 * @param suffixName
	 */
	public void exportExcel(ArrayList<Optional<RoomMortgageCashflow>> result, String pathName, String excelName,
			String suffixName) {
		// 创建 Workbook
		Workbook wb = createExcel(result, suffixName);

		String fileName = pathName + excelName + "." + suffixName;

		// 生成 excel文件
		writeFile(wb, fileName);
	}

	/**
	 * 创建excel文件
	 * 
	 * @param result
	 * @param suffixName
	 * @return
	 */
	private Workbook createExcel(ArrayList<Optional<RoomMortgageCashflow>> result, String suffixName) {
		// 通过 suffixName 获取 WorkBook
		Workbook wb = getWorkBook(suffixName);

		// 创建 sheet
		Sheet sheet = createSheet(wb);

		// 创建结果值 行列 并赋值
		createRowAndCellAndSetCellValue(result, sheet);

		// 设置 sheet列的格式
		setSheetColumnStyle(sheet);

		return wb;
	}

	/**
	 * 根据传入的后缀名suffixName，创建webbook对象
	 * 
	 * @param suffixName
	 * @return
	 */
	private Workbook getWorkBook(String suffixName) {
		Workbook wb = null;

		if (suffixName == "xls") {
			// 创建一个webbook，对应一个Excel文件
			wb = new HSSFWorkbook();
		} else if (suffixName == "xlsx") {
			// 创建一个webbook，对应一个Excel文件
			wb = new XSSFWorkbook();
		} else {
			System.out.println("excel后缀名未知，无法处理。请检查！");
		}
		return wb;
	}

	/**
	 * 创建sheet
	 * 
	 * @param wb
	 * @return
	 */
	private Sheet createSheet(Workbook wb) {
		// 在webbook中添加一个sheet
		String safeName = WorkbookUtil.createSafeSheetName("还款现金流");
		Sheet sheet = wb.createSheet(safeName);
		// 在sheet中添加表头第0行
		int rowNum = 0;
		Row row = sheet.createRow((short) rowNum);
		// 创建单元格，并设置值表头 设置表头居中
		CellStyle style = wb.createCellStyle();
		// 居中格式
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置列名
		setCellNameAndStyle(row, style);

		return sheet;
	}

	/**
	 * 设置列名
	 * 
	 * @param row
	 * @param style
	 */
	private void setCellNameAndStyle(Row row, CellStyle style) {
		// 设置列名
		Cell cell = row.createCell(0);
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
	}

	/**
	 * 传入要导出的值，赋值给对应的行列
	 * 
	 * @param result
	 * @param sheet
	 */
	private void createRowAndCellAndSetCellValue(ArrayList<Optional<RoomMortgageCashflow>> result, Sheet sheet) {
		Row row;
		// 创建行列，并赋值
		for (int i = 0; i < result.size(); i++) {
			// jdk 8 新特性 Optional
			Optional<RoomMortgageCashflow> ormc = result.get(i);
			// 检查Optional类型对象是否有值
			if (ormc.isPresent()) {
				// 获取对象
				RoomMortgageCashflow rmc = ormc.get();

				row = sheet.createRow((short) i + 1);
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
	}

	/**
	 * 设置 sheet列的格式
	 * 
	 * @param sheet
	 */
	private void setSheetColumnStyle(Sheet sheet) {
		// 设置自动列大小
		for (short i = sheet.getRow(0).getFirstCellNum(), end = sheet.getRow(0).getLastCellNum(); i < end; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	/**
	 * 生成 excel文件
	 * 
	 * @param wb
	 * @param fileName
	 */
	private void writeFile(Workbook wb, String fileName) {
		// 生成excel文件
		try {
			FileOutputStream out = new FileOutputStream(fileName);
			wb.write(out);
			out.flush();
			out.close();
			wb.close();
		} catch (Exception e) {
			System.out.println("excel文件写入磁盘失败！");
			e.printStackTrace();
		}
	}
}
