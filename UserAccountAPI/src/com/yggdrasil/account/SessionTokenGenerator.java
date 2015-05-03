package com.yggdrasil.account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionTokenGenerator {
	private static Logger logger = LogManager.getLogger(SessionTokenGenerator.class);
	
	public static String getSessionToken() {
		UUID uuid = UUID.randomUUID();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss.SSSZ");
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Bangkok/Thailand"), Locale.US);
		return(dateFormat.format(cal.getTime()) + "-" + uuid.toString());
	}
}
