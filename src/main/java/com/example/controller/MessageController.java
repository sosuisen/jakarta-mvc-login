package com.example.controller;

import java.sql.SQLException;
import com.example.model.MessageDTO;
import com.example.model.MessagesDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Controller
@RequestScoped
@Path("/")
public class MessageController {
	@Inject
	private Models models;
	@Inject
	private MessagesDAO messagesDAO;
	@Inject
	private HttpServletRequest req;

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

	@GET
	@Path("logout")
	public String logout() {
		try {
			req.logout();
			req.getSession().invalidate();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GET
	@Path("messages")
	public String getMessages() throws SQLException {
		models.put("userName", req.getRemoteUser());
		models.put("messages", messagesDAO.getAll());
		return "messages.html";
	}

	@POST
	@Path("messages")
	public String postMessages(@BeanParam MessageDTO mes) throws SQLException {
		mes.setName(req.getRemoteUser());
		messagesDAO.create(mes);
		return "redirect:messages";
	}
}
