package com.ontotext.graphdb.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Stateless
@Path("/semantic")
public class SemanticService {

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getString(String json) throws Exception {
		System.out.println(json);
		JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
		System.out.println(obj);
		return obj.toString();
	}
}
