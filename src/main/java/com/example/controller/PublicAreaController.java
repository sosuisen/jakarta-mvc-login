package com.example.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.MvcContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Controller
@RequestScoped
@Path("/")
public class PublicAreaController {
	@Inject
	private Models models;
	@Inject
	private MvcContext mvcContext;

	@PostConstruct
	public void setModels() {
		models.put("root", mvcContext.getBasePath());
	}

	@GET
	public String home() {
		models.put("appName", "Message Board");
		return "index.html";
	}

	@GET
	@Path("login")
	public String login(@QueryParam("error") final String error) {
		models.put("error", error);
		return "login.html";
	}
}
