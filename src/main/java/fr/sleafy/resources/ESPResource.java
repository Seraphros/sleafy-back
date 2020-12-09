package fr.sleafy.resources;

import com.codahale.metrics.annotation.Timed;
import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.controllers.ESPController;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.security.User;
import io.dropwizard.auth.Auth;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/esp")
@Produces(MediaType.APPLICATION_JSON)
@Api(value="/esp")
@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                basicAuthDefinitions = @BasicAuthDefinition(key = "espAuth")))
public class ESPResource {

    private final ESPController espController;

    public ESPResource(ESPDao espDao) {
        this.espController = new ESPController(espDao);
    }

    @PUT
    @Timed
    @ApiOperation(value = "Declare a new ESP")
    public IDSecretKey declareESP(@QueryParam("userID") int userID) {
        return espController.createNewESP(userID);
    }

    @GET
    @Timed
    @ApiOperation(value = "Get all ESPs according to the user")
    public List<ESP> getUsersESP(@QueryParam("userID") int userID) {
        return espController.getUsersESP(userID);
    }

    @GET
    @Path("/{uuid}")
    @Timed
    @ApiOperation(value = "Retrieve ESP according to its UUID", authorizations = @Authorization(value = "espAuth"))
    public ESP getESPfromUUID(@PathParam("uuid") String uuid, @ApiParam(hidden = true) @Auth User user) {
        return espController.getESPfromUUID(uuid);
    }

    @POST
    @Path("/{uuid}/name")
    @Timed
    @ApiOperation(value = "Change ESP Name")
    public ESP getESPfromUUID(@PathParam("uuid") String uuid, @QueryParam("name") String name) {
        return espController.changeESPName(uuid, name);
    }
}
