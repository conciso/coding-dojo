package de.conciso.codingdojo;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/welcome")
public class WelcomeController {
    @Inject
    Validator validator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postName(Message name) {
        if (name == null) {
            return Response.status(400).build();
        }

        Set<ConstraintViolation<Message>> violations = validator.validate(name);
        if (!violations.isEmpty()) {
            return Response.status(400).build();
        }
        MessageEntity storedMessage = Message.toStoredMessage(name);
        storedMessage.persistAndFlush();

        return Response.ok(ImmutableResultMessage.builder().message("Hello " + name.getName()).build()).build();
    }
}