package br.edu.ifpr.tempconv.restful.xml.adapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>{
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
	
	@Override
	public LocalDateTime unmarshal(String v) throws Exception {
		return LocalDateTime.parse(v,dtf);
	}
	
	@Override
	public String marshal(LocalDateTime v) throws Exception {
		return v.format(dtf);
	}

}
