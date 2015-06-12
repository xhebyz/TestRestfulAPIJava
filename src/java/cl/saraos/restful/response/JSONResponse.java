/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.saraos.restful.response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Seba { "code": "00", "description": "OK", "data": "HOLA" }
 */
public class JSONResponse extends JSONObject {

    public JSONResponse() {
        super();
    }

    public JSONResponse(String data) throws JSONException {
        super();
        this.put("code", "00");
        this.put("description", "OK");
        this.put("data", data);

    }

    public JSONResponse(String data, String description, String code) throws JSONException {
        super();
        this.put("code", code);
        this.put("description", description);
        this.put("data", data);

    }

}
