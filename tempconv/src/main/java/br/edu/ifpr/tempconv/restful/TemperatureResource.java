package br.edu.ifpr.tempconv.restful;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.repository.TemperatureMongoRepository;
import br.edu.ifpr.tempconv.utils.TemperatureConverter;
import br.edu.ifpr.tempconv.utils.TemperatureUtils;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;

@Path("temperatures")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class TemperatureResource {

    private TemperatureMongoRepository repo = TemperatureMongoRepository.INSTANCE;

    public TemperatureResource() {}

    @POST
    public Response insert(@Context UriInfo uriInfo, @BeanParam Temperature t) {
        Double tempo = TemperatureConverter.calculateTempOutput(
                t.getTypei(),
                t.getTempi(),
                t.getTypeo()
        );

        Temperature temp = new Temperature(
                t.getTempi(),
                t.getTypei(),
                tempo,
                t.getTypeo()
        );

        repo.insert(temp);

        Long millis = TemperatureUtils.timestampToMillis(temp.getTimestamp());
        String id = TemperatureUtils.timestampToString(millis);

        UriBuilder builder = uriInfo.getBaseUriBuilder();
        URI uri = builder.path("temperatures")
                         .path(id)
                         .build();

        return Response.created(uri).build();
    }

    @PUT
    @Path("{_id}")
    public Response update(@PathParam("_id") String id, @BeanParam Temperature temperature) {

        boolean updated = repo.update(temperature);
        String json = """
                      {
                         "_id": "%s",
                         "updated": %b
                      }
                      """.formatted(id, updated);

        return Response.ok(json).build();
    }

    @DELETE
    public Response delete() {
        int deleted = repo.delete();
        String json = """
                      {
                         "deleted_all": %d
                      }
                      """.formatted(deleted);

        return Response.ok(json).build();
    }

    @DELETE
    @Path("{_id}")
    public Response delete(@PathParam("_id") String id) {
        boolean deleted = repo.delete(id);
        String json = """
                      {
                         "deleted": %b
                      }
                      """.formatted(deleted);

        return Response.ok(json).build();
    }

    @GET
    public Response getTemperatures() {
        List<Temperature> temperatures = repo.findAll();
        GenericEntity<List<Temperature>> entity =
                new GenericEntity<List<Temperature>>(temperatures) {};

        return Response.ok(entity).build();
    }

    @GET
    @Path("{_id}")
    public Response getTemperatureById(@PathParam("_id") String id) {
        Optional<Temperature> temperature = repo.findById(id);

        if (temperature.isPresent())
            return Response.ok(temperature.get()).build();

        String json = """
                      {
                         "_id": "%s",
                         "found": false
                      }
                      """.formatted(id);

        return Response.status(Status.NOT_FOUND)
                       .entity(json)
                       .build();
    }
}
