package br.edu.ifpr.tempconv.restful;

import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("converter")
public class TemperatureConverterResource {
	@GET @Path("temppp/{ti}/{tiv:[-+]?\\*d([.,]\\*d+)?}/{to}")
	public Response temppp(@PathParam("ti") TemperatureTypes ti,
						   @PathParam("tiv") String tiv, 
						   @PathParam("to") TemperatureTypes to) {
		return Response.ok().build();
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
	}
}
