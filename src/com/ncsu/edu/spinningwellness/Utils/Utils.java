package com.ncsu.edu.spinningwellness.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static long convertDateToString(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		return Long.parseLong(df.format(date));
	}

	public static Date convertStringToDate(Long date) {
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		try {
			return df.parse(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
