package br.edu.ifpr.tempconv.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TemperatureUtils {
	public static Long timestampToMillis(LocalDateTime ldt) {
		return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static String timestampToString(long timestamp) {
		return String.valueOf(timestamp);
	}

}
