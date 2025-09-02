package br.edu.ifpr.tempconv.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import br.edu.ifpr.tempconv.model.types.TemperatureTypes;

public class Temperature {
	private LocalDateTime timestamp;
	private Double tempi;
	private TemperatureTypes typei;
	private Double tempo;
	private TemperatureTypes typeo;

	public Temperature() {}
	public Temperature(Double tempi, TemperatureTypes typei,
			   Double tempo, TemperatureTypes typeo){
	this.timestamp = LocalDateTime.now();
	this.tempi = tempi;
	this.typei = typei;
	this.tempo = tempo;
	this.typeo = typeo;
	}
public String toString(){
	DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG,
								      FormatStyle.SHORT);

	NumberFormat nf = NumberFormat.getInstance();
	StringBuffer sb = new StringBuffer();

	nf.setMaximumFractionDigits(2);
	
	sb.append("Temperature [").append("\n")
	  .append("timestamp=").append(timestamp.format(dtf)).append("\n")
	  .append("temp input=").append(nf.format(tempi))
				.append(" (")
				.append(typei.description())
				.append(" ")
				.append(typei.symbol())
				.append(")")
	  .append("\n")
	  .append("temp output=").append(nf.format(tempo))
				 .append(" (").append(typeo.description())
				 .append(" ")
				 .append(typeo.symbol())
				 .append(")")
	  .append("\n")
	  .append("]");
	return sb.toString();
	
	}
}
