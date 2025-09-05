package br.edu.ifpr.tempconv.restful;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("converter")
@Produces(MediaType.APPLICATION_JSON)
public class TemperatureConverterResource {
	@GET @Path("temppp/{ti}/{tiv:[-+]?\\d*([.,]\\d+)?}/{to}")
	public Response temppp(@PathParam("ti") TemperatureTypes ti,
				              @PathParam("tiv") String tiv,
				              @PathParam("to") TemperatureTypes to) {
		//return processTemperature(ti,tiv,to);
		String str   = tiv.replace(',','.');
	    Double tempi = Double.valueOf(str);
		Double tempo = TemperatureConverter.calculateTempOutput(ti,tempi,to);
	
		Temperature temp  = new Temperature(tempi,ti,tempo,to);
	
	    return Response.ok(ti)
			               .entity(temp.toString())
			               .header("Content-Type",MediaType.TEXT_PLAIN +
			                                      ";charset=utf-8")
			               .build();
	}

	@GET @Path("tempqp")
	public Response tempqp(@QueryParam("ti") TemperatureTypes ti,
				              @QueryParam("tiv") String tiv,
				              @QueryParam("to") TemperatureTypes to) {
	   if (tiv == null || !tiv.matches("^[-+]?\\d*([.,]\\d+)?$"))
			return Response.status(Status.BAD_REQUEST) // 400
						      .entity("Temperatura inválida.")
			               .header("Content-Type",MediaType.TEXT_PLAIN +
                                 ";charset=utf-8")
						      .build();

	   //return processTemperature(ti,tiv,to);
	   String str   = tiv.replace(',','.');
	   Double tempi = Double.valueOf(str);
		Double tempo = TemperatureConverter.calculateTempOutput(ti,tempi,to);
//
	   Temperature temp  = new Temperature(tempi,ti,tempo,to);
//
       return Response.ok()
		               .entity(temp.toString())
		               .header("Content-Type",MediaType.TEXT_PLAIN +
	                                      ";charset=utf-8")
		               .build();
	}

	
	
	
	private Response processTemperature(TemperatureTypes ti, String tiv,
				                          TemperatureTypes to) {
	   String str   = tiv.replace(',','.');
	   Double tempi = Double.valueOf(str);
	   Double tempo = TemperatureConverter.calculateTempOutput(ti,tempi,to);

	   Temperature temp  = new Temperature(tempi,ti,tempo,to);

      return Response.ok()
		               .entity(temp)
		              // .header("Content-Type",MediaType.TEXT_PLAIN +
		                                 //     ";charset=utf-8")
		               .build();
	}
}
