/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.saraos.restful.status;

/**
 *
 * @author Seba
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/status")
public class StatusVersion {

    private static final String api_version = "0.1";

    @Path("/version")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String returnAPIVersion() {
        return "<p>Version de la API: "+ api_version+"</p>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String returnHelloWorld() {
        return "<p>Hola Mundo!! RESTful API</p>";
    }

}
