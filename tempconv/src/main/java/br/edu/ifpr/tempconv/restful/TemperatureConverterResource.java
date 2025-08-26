package br.edu.ifpr.tempconv.restful;

import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("converter")
public class TemperatureConverterResource {
	@GET @Path("c2fpp/{celsius}")
	public Response c2fpp(@PathParam("celsius") Double celsius) {
		Double fahrenheit = TemperatureConverter.celsius2Fahrenheit(celsius);
		return Response.ok().entity(fahrenheit).build();
	}
	
	
}
