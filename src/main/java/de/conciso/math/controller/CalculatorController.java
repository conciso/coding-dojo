package de.conciso.math.controller;

import de.conciso.math.service.Calculator;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public class CalculatorController {

  @Inject
  private Calculator calculator;

  @GET
  @Produces("text/plain")
  @Path("/addition/{firstSummand}/{secondSummand}")
  public int getAddition(@PathParam("firstSummand") int firstSummand,
      @PathParam("secondSummand") int secondSummand) {
    return calculator.addition(firstSummand, secondSummand);
  }

  @GET
  @Produces("text/plain")
  @Path("/multiplication/{multiplier}/{multiplicand}")
  public int getMultiplication(@PathParam("multiplier") int multiplier,
      @PathParam("multiplicand") int multiplicand) {
    return calculator.multiplication(multiplier, multiplicand);
  }
}
