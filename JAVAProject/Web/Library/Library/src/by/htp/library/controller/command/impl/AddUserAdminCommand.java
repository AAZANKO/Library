package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.controller.command.Command;
import by.htp.library.entity.User;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;
import by.htp.library.service.exception.ServiceException;

public class AddUserAdminCommand implements Command {
	
	public AddUserAdminCommand() {}

	private String firstName;
	private String lastName;
	private String midleName;
	private String login;
	private String password;
	private String eMail;
	private String role;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// добавление нового пользователя администратором,

		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}		
		
		this.firstName = request.getParameter("firstname");
		this.lastName = request.getParameter("lastname");
		this.midleName = request.getParameter("midleName");
		this.login = request.getParameter("login");
		this.password = request.getParameter("password");
		this.eMail = request.getParameter("eMail");
		this.role = request.getParameter("role");

		User user;
		String goToPage;
		
		ArrayList<User> arrUser = new ArrayList<User>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {
			user = userService.adminregistruser(this.firstName, this.lastName, this.midleName, this.login,
					this.password, this.eMail, this.role);
			
			arrUser.add(user);
			
			if (user != null) {
				// request.setAttribute("myuser", user); // для передачи параметров на страницу
				request.setAttribute("alluser", arrUser); 
				if (user.getRole() == 1) {
					goToPage = "/WEB-INF/jsp/Administrator.jsp";
				} else {
					goToPage = "/WEB-INF/jsp/Users.jsp";
				}
			} else {
				request.setAttribute("errorMessage", "no such user");
				goToPage = "error.jsp";
			}
		} catch (ServiceException e) {
			// logging нужно дописать
			e.printStackTrace();// stub
			request.setAttribute("errorMessage", "no such user");
			goToPage = "error.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);

	}

}
