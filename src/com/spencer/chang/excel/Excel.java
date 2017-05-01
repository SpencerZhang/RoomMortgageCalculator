package com.spencer.chang.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
	 * @param sheetData
	 * @param pathName
	 * @param excelName
	 * @param suffixName
	 * @param sheetName
	 * @param columnName
	 */
	public void exportExcel(HashMap<String, ArrayList<Optional<RoomMortgageCashflow>>> sheetData, String pathName,
			String excelName, String suffixName, HashMap<String, String> sheetName,
			HashMap<String, ArrayList<String>> columnName) {
		// 创建 Workbook
		Workbook wb = createExcel(sheetData, suffixName, sheetName, columnName);

		String fileName = pathName + excelName + "." + suffixName;

		// 生成 excel文件
		writeFile(wb, fileName);
	}

	/**
	 * 创建excel文件
	 * 
	 * @param sheetData
	 * @param suffixName
	 * @param sheetName
	 * @param columnName
	 * @return
	 */
	private Workbook createExcel(HashMap<String, ArrayList<Optional<RoomMortgageCashflow>>> sheetData,
			String suffixName, HashMap<String, String> sheetName, HashMap<String, ArrayList<String>> columnName) {
		// 通过 suffixName 获取 WorkBook
		Workbook wb = getWorkBook(suffixName);

		// 创建 sheet
		ArrayList<Sheet> sheets = createSheet(wb, sheetName, columnName);

		// java 8 Lambda Expression
		sheets.forEach(sheet -> {
			String sn = sheet.getSheetName();
			sheetData.forEach((k, v) -> {
				String sheetNameValue = sheetName.get(k);
				if (sheetNameValue.equalsIgnoreCase(sn)) {
					// 创建结果值 行列 并赋值
					createRowAndCellAndSetCellValue(v, sheet);
				}
			});

			// 设置 sheet列的格式
			setSheetColumnStyle(sheet);

		});

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
	 * @param sheetName
	 * @param columnName
	 * @return
	 */
	private ArrayList<Sheet> createSheet(Workbook wb, HashMap<String, String> sheetName,
			HashMap<String, ArrayList<String>> columnName) {
		ArrayList<Sheet> sheets = new ArrayList<Sheet>();
		// java 8 Lambda Expression
		sheetName.forEach((k, v) -> {
			// 在webbook中添加一个sheet
			String safeName = WorkbookUtil.createSafeSheetName(v);
			Sheet sheet = wb.createSheet(safeName);
			// 在sheet中添加表头第0行
			int rowNum = 0;
			Row row = sheet.createRow((short) rowNum);
			// 创建单元格，并设置值表头 设置表头居中
			CellStyle style = wb.createCellStyle();
			// 居中格式
			style.setAlignment(HorizontalAlignment.CENTER);
			columnName.forEach((kc, vc) -> {
				// if (kc.equals(k)) {
				// if (kc == k) {
				if (kc.equalsIgnoreCase(k)) {// 比较两个字符串的内容是否相同，忽略大小写
					// 设置列名
					setCellNameAndStyle(row, style, vc);
				}
			});
			sheets.add(sheet);
		});

		return sheets;
	}

	/**
	 * 设置列名
	 * 
	 * @param row
	 * @param style
	 * @param columnNameValue
	 */
	private void setCellNameAndStyle(Row row, CellStyle style, ArrayList<String> columnNameValue) {
		int columnNum = 0;
		for (String cnv : columnNameValue) {
			// 设置列名
			Cell cell = row.createCell(columnNum);
			cell.setCellValue(cnv);
			cell.setCellStyle(style);
			columnNum++;
		}
	}

	/**
	 * 传入要导出的值，赋值给对应的行列
	 * 
	 * @param sheetData
	 * @param sheet
	 */
	private void createRowAndCellAndSetCellValue(ArrayList<Optional<RoomMortgageCashflow>> sheetData, Sheet sheet) {
		Row row;
		// 创建行列，并赋值
		for (int i = 0; i < sheetData.size(); i++) {
			// jdk 8 新特性 Optional
			Optional<RoomMortgageCashflow> ormc = sheetData.get(i);
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
		
		for (short i = sheet.getRow(0).getFirstCellNum(), end = sheet.getRow(0).getLastCellNum(); i < end; i++) {
			// 设置自动列大小
			sheet.autoSizeColumn(i);
			// 设置列头的过滤
			CellRangeAddress ca = new CellRangeAddress(0, sheet.getLastRowNum(), sheet.getRow(0).getFirstCellNum(),
					sheet.getRow(0).getLastCellNum()-1);
			sheet.setAutoFilter(ca);
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
