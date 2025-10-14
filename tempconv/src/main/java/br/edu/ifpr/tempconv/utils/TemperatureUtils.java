package br.edu.ifpr.tempconv.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.bson.Document;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.model.types.TemperatureTypes;

public final class TemperatureUtils {
	public static Long timestampToMillis(LocalDateTime ldt) {
		return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static String timestampToString(long timestamp) {
		return String.valueOf(timestamp);
	}
	
	public static Temperature fromDocument(Document doc) {
        Temperature temperature = new Temperature();

        String timestampStr = doc.getString("timestamp");
        temperature.setTimestamp(LocalDateTime.parse(timestampStr));

        temperature.setTempi(doc.getDouble("tempi"));
        temperature.setTempo(doc.getDouble("tempo"));

        temperature.setTypei(TemperatureTypes.valueOf(doc.getString("typei")));
        temperature.setTypeo(TemperatureTypes.valueOf(doc.getString("typeo")));

        return temperature;
    }
}
