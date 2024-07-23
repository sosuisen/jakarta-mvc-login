package com.example.controller;

import java.sql.SQLException;
import com.example.model.MessageDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Controller
@RequestScoped
@Path("/")
public class MessagesController {
	@Inject
	private Models models;
	@Inject
	private MessageDAO messagesDAO;
	@Inject
	private HttpServletRequest req;

	@GET
	@Path("messages")
	public String getMessages() throws SQLException {
		models.put("userName", req.getRemoteUser());
		models.put("messages", messagesDAO.getAll());
		return "messages.html";
	}

	@POST
	@Path("messages")
	public String postMessages(@FormParam("message") String mes) throws SQLException {
		messagesDAO.create(req.getRemoteUser(), mes);
		return "redirect:messages";
	}

	@GET
	@Path("logout")
	public String logout() {
		try {
			req.logout();
			req.getSession().invalidate();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:";
	}
}
