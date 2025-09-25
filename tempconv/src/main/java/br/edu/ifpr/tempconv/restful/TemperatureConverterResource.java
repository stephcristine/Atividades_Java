package br.edu.ifpr.tempconv.restful;

import java.net.URI;
//import java.time.ZoneId;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import br.edu.ifpr.tempconv.repository.TemperatureRepository;
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import br.edu.ifpr.tempconv.utils.TemperatureUtils;
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
	private TemperatureRepository repo = TemperatureRepository.INSTANCE;
	
	@GET @Path("temppp/{ti}/{tiv:[-+]?\\d+(?:[.,]\\d+)?}/{to}")
	public Response temppp(@PathParam("ti") TemperatureTypes ti,
						   @PathParam("tiv") String tiv, 
						   @PathParam("to") TemperatureTypes to) {
		return processTemperature(ti,tiv,to);
	}
	
	@GET @Path("tempqp")
	public Response tempqp(@QueryParam("ti") TemperatureTypes ti,
						   @QueryParam("tiv") String tiv,
						   @QueryParam("to") TemperatureTypes to) {
		
		if (tiv == null || !tiv.matches("[-+]?\\d+(?:[.,]\\d+)?")) {
			return Response.status(Response.Status.BAD_REQUEST)
                    .entity(tiv)
                    .build();
	    }
		
		return processTemperature(ti,tiv,to);
	}
	
	@POST @Path("form1")
	public Response FormParam(@Context UriInfo uriInfo,
							  @BeanParam Temperature t) {
		Double tempo = TemperatureConverter.calculateTempOutput(t.getTypei(),
																t.getTempi(),
																t.getTypeo());
		Temperature temp = new Temperature(t.getTempi(), t.getTypei(),
										   tempo, t.getTypeo());
		
		repo.insert(temp);
		
		Long millis = TemperatureUtils.timestampToMillis(t.getTimestamp());
		
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
		
		Temperature temp = new Temperature(tempi,ti,tempo,to);
		
		return Response.ok()
					   .entity(temp)
					   .build();
	}
}
