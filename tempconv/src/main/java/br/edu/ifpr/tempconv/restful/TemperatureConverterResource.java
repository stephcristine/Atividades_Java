package br.edu.ifpr.tempconv.restful;

import java.net.URI;
import java.time.ZoneId;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("converter")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TemperatureConverterResource {
	@GET @Path("temppp/{ti}/{tiv:[-+]?\\*d([.,]\\*d+)?}/{to}")
	public Response temppp(@PathParam("ti") TemperatureTypes ti,
						   @PathParam("tiv") String tiv, 
						   @PathParam("to") TemperatureTypes to) {
		return processTemperature(ti,tiv,to);
		// SUBSTITUIR ',' POR '.'
		// CONVERTER DE STRING PARA DOUBLE
		// INVOCAR TemperatureConverter.calculateTempOutput
		// CRIAR O OBJETO Temperature
		// "MONTAR" / DEVOLVER A RESPOSTA COM O OBJETO Temperature
	}
	
	@GET @Path("tempqp")
	public Response tempqp(@QueryParam("ti") TemperatureTypes ti,
						   @QueryParam("tiv") String tiv,
						   @QueryParam("to") TemperatureTypes to) {
		return Response.ok().build();
		// SE "tiv" É INVÁLIDO OU NÃO PASSA NA VERIFICAÇÃO
		// 	DA REGEX
		// 	ENTÃO RETORNAR 400
		// FIM-SE
		//
		// SUBSTITUIR ',' POR '.'
		// CONVERTER DE STRING PARA DOUBLE
		// INVOCAR TemperatureConverter.calculateTempOutput
		// CRIAR O OBJETO Temperature
		// "MONTAR" / DEVOLVER A RESPOSTA COM O OBJETO Temperature
		
		// return processTemperature(ti,tiv,to);
	}
	
	@POST @Path("form1")
	public Response FormParam(@Context UriInfo uriInfo,
							  @BeanParam Temperature t) {
		Double tempo = TemperatureConverter.calculateTempOutput(t.getTypei(),
																t.getTempi(),
																t.getTypeo());
		Temperature temp = new Temperature(t.getTempi(), t.getTypei(),
										   tempo, t.getTypeo());
		
		Long millis = temp.getTimestamp().atZone(ZoneId.systemDefault())
										 .toInstant()
										 .toEpochMilli();
		
		UriBuilder builder = uriInfo.getBaseUriBuilder();
		URI uri = builder.path("temperatures")
						 .path(String.valueOf(millis))
						 .build();
		
		return Response.created(uri).build();
		// return Response.ok(temp).location(uri).build();
	}
	
	private Response processTemperature(TemperatureTypes ti, String tiv, TemperatureTypes to) {
		String str = tiv.replace(',', '.');
		Double tempi = Double.valueOf(str);
		Double tempo = TemperatureConverter.calculateTempOutput(ti, tempi, to);
		
		
		
	}
}
