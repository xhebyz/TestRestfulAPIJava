/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.saraos.restful.test;

/**
 *
 * @author Seba
 */
import cl.saraos.restful.response.JSONResponse;
import cl.saraos.utils.PrettyDate;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/test")
public class TestAPI {

    @Path("/word")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * { "data": "hola" } { "code": "00", "description": "OK", "data": "HOLA" }
     * Recibe una palabra de largo 4 (String) y devuelve su valor en mayúsculas
     */

    public Response getWord(
            String request) throws JSONException {

        try {

            if (request == null || request.equals("")) {
                JSONResponse json = new JSONResponse(null, "Error: porfavor envie parametro", "333");
                return Response.status(400).entity(json.toString()).build();
            }

            JSONObject jsonRequest = new JSONObject(request);
            String word = jsonRequest.getString("data");
            if (word == null) {
                JSONResponse json = new JSONResponse(null, "Error: porfavor especifique el parametro", "33");
                return Response.status(400).entity(json.toString()).build();

            }
//            if (word.length() != 4) {
//                JSONResponse json = new JSONResponse(null, "Error: el parametro debe ser de largo 4", "333");
//                return Response.status(400).entity(json.toString()).build();
//            }

            if (NumberUtils.isNumber(word)) {
                JSONResponse json = new JSONResponse(null, "Error: el parametro no puede ser numerico", "33");
                return Response.status(400).entity(json.toString()).build();

            }

            String returnString;
            String dataUp = this.upperWords(word);

            JSONResponse json = new JSONResponse(dataUp);
            returnString = json.toString();

            return Response.ok(returnString).build();
        } catch (Exception e) {
            JSONResponse json = new JSONResponse(null, "Error del servidor", "66");
            return Response.status(500).entity(json.toString()).build();
        }
    }

    public String upperWords(String word) {
        String dataUp = word.toUpperCase();
        return dataUp;
    }

    @Path("/time")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * 2.- [GET] /time?value="hora" retorna: { "code": "00", "description":
     * "OK", "data": "UTC ISO DATE" }
     */

    public Response getTime(
            @QueryParam("value") String time) throws JSONException {
        String returnString = null;
        String utc = null;
        if (time == null) {
            JSONResponse json = new JSONResponse(null, "Error: porfavor especifique el parametro", "33");
            return Response.status(400).entity(json.toString()).build();
        }
        try {
            utc = this.getTimeUTC(time);
        } catch (Exception e) {
            JSONResponse json = new JSONResponse(null, "Error: parametro no valido para transformar a UTC", "33");
            return Response.status(400).entity(json.toString()).build();
        }
        try {

            JSONResponse json = new JSONResponse(utc);
            returnString = json.toString();

            return Response.ok(returnString).build();
        } catch (Exception e) {
            JSONResponse json = new JSONResponse(null, "Error del servidor", "66");
            return Response.status(500).entity(json.toString()).build();
        }

    }

    /**
     * Transformar value a date utc
     *
     * @param value
     * @return
     * @throws ParseException
     */
    public String getTimeUTC(String value) throws ParseException {

        String[] val = value.split(":");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(val[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(val[1]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        String utcDate = PrettyDate.toLegacyString(date);

        return utcDate;
    }
}
