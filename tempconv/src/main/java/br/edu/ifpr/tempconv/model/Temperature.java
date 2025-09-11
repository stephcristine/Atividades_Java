package br.edu.ifpr.tempconv.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import br.edu.ifpr.tempconv.restful.xml.adapter.LocalDateTimeAdapter;
import jakarta.ws.rs.FormParam;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder= {"timestamp","typei","tempi","typeo","tempo"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Temperature {
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime timestamp;
	
	//Exemplo formatação
	@JsonProperty("temperatura_entrada")
	@XmlElement(name="temp_entrada")
	@FormParam("tempi")
	private Double tempi;
		
	@JsonProperty("tipo_temperatura_entrada")
	@XmlElement(name="typei")
	@FormParam("typei")
	private TemperatureTypes typei;
	
	@JsonProperty("Temperatura_saída")
	@XmlElement(name="temp_saída")
	private Double tempo;
	
	@JsonProperty("tipo_temperatura_saída")
	@XmlElement(name="typeo")
	@FormParam("typeo")
	private TemperatureTypes typeo;

	public Temperature() {
		this.timestamp = LocalDateTime.now();
	}
	public Temperature(Double tempi, TemperatureTypes typei,
					   Double tempo, TemperatureTypes typeo){
	this.timestamp = LocalDateTime.now();
	this.tempi = tempi;
	this.typei = typei;
	this.tempo = tempo;
	this.typeo = typeo;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public LocalDateTime getTimestamp() { return timestamp;}
	
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
