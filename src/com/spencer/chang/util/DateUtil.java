package com.spencer.chang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理日期
 * 
 * @author Spencer
 *
 */
public class DateUtil {
	public static String getDate() {
		String dueDate = null;
		if (PlatformJavaVersion.javaVersion() == 8) {
			// JDK8 新特性 直接返回 2017-04-24 还款日期
			dueDate = LocalDate.now().toString();
		} else {
			dueDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		return dueDate;
	}

	public static Date getDate(String date) {
		Date dueDate = null;
		try {
			dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dueDate;
	}

	public static String addDays(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);//加n月
		String dueDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return dueDate;
	}

}
