package br.edu.ifpr.tempconv.restful;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("converterpp")
public class TemperatureConverterPPResource {
	@GET @Path("c2fpp/{tempi:[-+]?\\d*([.,]\\d+)?}")
	public Response c2fpp(@PathParam("tempi") String temperature) {
		String str = temperature.replace(',','.');
		
		Double tempi = Double.valueOf(str);
		Double tempo = TemperatureConverter.celsius2Fahrenheit(tempi);
		Temperature  temp = new Temperature(tempi, TemperatureTypes.CELSIUS,
											tempo, TemperatureTypes.FAHRENHEIT);
					
		return Response.ok()
						.entity(temp.toString())
						.header("Content-Type",MediaType.TEXT_PLAIN + 
											   ";charset=utf-8")
						.build();
	}
	
	@GET @Path("c2kpp/{tempi}")
	public Response c2kpp(@PathParam("tempi") Double tempi) {
		Double tempo = TemperatureConverter.celsius2Kelvin(tempi);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("f2cpp/{tempi}")
	public Response f2cpp(@PathParam("tempi") Double tempi) {
		Double tempo = TemperatureConverter.fahrenheit2Celsius(tempi);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("f2kpp/{tempi}")
	public Response f2kpp(@PathParam("tempi") Double tempi) {
		Double tempo = TemperatureConverter.fahrenheit2Kelvin(tempi);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("k2cpp/{tempi}")
	public Response k2cpp(@PathParam("tempi") Double tempi) {
		Double tempo = TemperatureConverter.kelvin2Celsius(tempi);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("k2fpp/{tempi}")
	public Response k2fpp(@PathParam("tempi") Double tempi) {
		Double tempo = TemperatureConverter.kelvin2Fahrenheit(tempi);
		return Response.ok().entity(tempo).build();
	}

}
