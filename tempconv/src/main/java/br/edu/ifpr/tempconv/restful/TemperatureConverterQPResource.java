package br.edu.ifpr.tempconv.restful;

import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("converterqp")
public class TemperatureConverterQPResource {
	@GET @Path("c2fqp")
	public Response c2fqp(@QueryParam("ti") Double ti) {
		Double tempo = TemperatureConverter.celsius2Fahrenheit(ti);
		
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("c2kqp")
	public Response c2kqp(@QueryParam("ti") Double ti) {
		Double tempo = TemperatureConverter.celsius2Kelvin(ti);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("f2cqp")
	public Response f2cqp(@QueryParam("ti") Double ti) {
		Double tempo = TemperatureConverter.fahrenheit2Celsius(ti);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("f2kqp")
	public Response f2kqp(@QueryParam("ti") Double ti) {
		Double tempo = TemperatureConverter.fahrenheit2Kelvin(ti);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("k2cqp")
	public Response k2cpp(@QueryParam("ti") Double ti) {
		Double tempo = TemperatureConverter.kelvin2Celsius(ti);
		return Response.ok().entity(tempo).build();
	}
	
	@GET @Path("k2fqp")
	public Response k2fpp(@QueryParam("ti") Double ti) {
		Double tempo = TemperatureConverter.kelvin2Fahrenheit(ti);
		return Response.ok().entity(tempo).build();
	}

}
