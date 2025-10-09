package br.edu.ifpr.tempconv.restful;

import java.net.URI;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.repository.TemperatureMongoRepository;
import br.edu.ifpr.tempconv.repository.TemperatureRepository;
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import br.edu.ifpr.tempconv.utils.TemperatureUtils;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("Temperatures")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TemperatureResource {
	private TemperatureMongoRepository repo = TemperatureMongoRepository.INSTANCE;
	
	public TemperatureResource() {}
	
	@POST @Path("create")
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
	}
	
}
