package com.girmiti.nexo.util;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static String startTime = " 00:00:00";
	private static String endTime = " 23:59:59";

	public static Timestamp setGenerationDateTimeResponse(Timestamp generationDateStart) {
		if (generationDateStart != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(generationDateStart.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(generationDateStart.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(generationDateStart.toString().substring(8, 10)));
			time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(generationDateStart.toString().substring(11, 13)) + 6);
			time.set(Calendar.MINUTE, Integer.parseInt(generationDateStart.toString().substring(14, 16)) - 30);
			time.set(Calendar.SECOND, Integer.parseInt(generationDateStart.toString().substring(17, 19)));
			return Timestamp.valueOf(sdf.format(time.getTime()));
		} else {
			return null;
		}
	}

	public static Timestamp setGenerationDateStartRequest(Timestamp generationDateStart) {
		if (generationDateStart != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(generationDateStart.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(generationDateStart.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(generationDateStart.toString().substring(8, 10)) - 1);
			time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(generationDateStart.toString().substring(11, 13)) + 19);
			time.set(Calendar.MINUTE, Integer.parseInt(generationDateStart.toString().substring(14, 16)) - 30);
			time.set(Calendar.SECOND, Integer.parseInt(generationDateStart.toString().substring(17, 19)));
			return Timestamp.valueOf(sdf.format(time.getTime()));
		} else {
			return null;
		}
	}

	private DateUtil() {
		super();
	}

	public static Timestamp convertStringToTimestamp(String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return new Timestamp((df.parse(date)).getTime());
		} catch (Exception e) {
			logger.error("DateUtil class :: convertStringToTimestamp method :: exception", e);
		}
		return null;
	}

	public static String toDateStringFormat(Timestamp date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		if (null != date) {
			dateFormat.applyPattern(pattern);
			return dateFormat.format(date);
		} else {
			return null;
		}
	}

	public static Timestamp toTimestamp(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		try {
			dateFormat.applyPattern(pattern);
			return new Timestamp(dateFormat.parse(date).getTime());
		} catch (ParseException e) {
			logger.info("ERROR:: DateUtil :: toTimestamp method : with date : " + date, e);
		}
		return null;
	}

	public static String appendFromTime(String date) {

		if (null != date) {
			if (date.contains(startTime.trim())) {
				// DO nothing
			} else {
				date += startTime;
			}
		}
		return date;

	}

	public static String appendToTime(String date) {

		if (null != date) {
			if (date.contains(endTime.trim())) {
				// DO nothing
			} else {
				date += endTime;
			}
		}
		return date;

	}

}
