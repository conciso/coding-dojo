package de.conciso.codingdojo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/welcome")
public class WelcomeController {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello RESTEasy";
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createWelcome(WelcomeRequest welcomeRequest) {
    return Response
        .created(URI.create("id"))
        .entity(new WelcomeResponse("Welcome " + welcomeRequest.name()))
        .build();
  }


}
