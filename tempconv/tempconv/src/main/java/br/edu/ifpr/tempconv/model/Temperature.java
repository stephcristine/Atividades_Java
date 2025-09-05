package br.edu.ifpr.tempconv.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.edu.ifpr.tempconv.model.types.TemperatureTypes;

public class Temperature {
   @JsonFormat(shape=JsonFormat.Shape.STRING,
			   pattern="yyyy-MM-dd@HH:mm:ss"/*,
			   locale="pt_BR"*/)
   private LocalDateTime    timestamp;
   private Double           tempi;
   private TemperatureTypes typei;
   private Double           tempo;
   private TemperatureTypes typeo;

   public Temperature() {}
   public Temperature(Double tempi, TemperatureTypes typei,
                      Double tempo, TemperatureTypes typeo) {
      this.timestamp = LocalDateTime.now();
      this.tempi     = tempi;
      this.typei     = typei;
      this.tempo     = tempo;
      this.typeo     = typeo;
   }

   public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
   }
   public LocalDateTime getTimestamp() { return timestamp; }

   public void setTempi(Double tempi) { this.tempi = tempi; }
   public Double getTempi() { return tempi; }

   public void setTypei(TemperatureTypes typei) { this.typei = typei; }
   public TemperatureTypes getTypei() { return typei; }

   public void setTempo(Double tempo) { this.tempo = tempo; }
   public Double getTempo() { return tempo; }

   public void setTypeo(TemperatureTypes typeo) { this.typeo = typeo; }
   public TemperatureTypes getTypeo() { return typeo; }

   @Override
   public String toString() {
      // formatar data e hora
      DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(
                                                             FormatStyle.LONG,
                                                             FormatStyle.SHORT);
      // formatar números de ponto flutuante
      NumberFormat      nf  = NumberFormat.getInstance();
      StringBuffer      sb  = new StringBuffer();

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
        .append("temp ouput=").append(nf.format(tempo))
                              .append(" (").append(typeo.description())
                              .append(" ")
                              .append(typeo.symbol())
                              .append(")")
        .append("\n")
        .append("]");

      return sb.toString();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;

      Temperature other = (Temperature) obj;

      return Objects.equals(tempi,other.tempi) &&
             Objects.equals(tempo,other.tempo) &&
             Objects.equals(timestamp,other.timestamp) &&
             typei == other.typei && typeo == other.typeo;
   }

   @Override
   public int hashCode() {
      return Objects.hash(tempi, tempo, timestamp, typei, typeo);
   }
}
