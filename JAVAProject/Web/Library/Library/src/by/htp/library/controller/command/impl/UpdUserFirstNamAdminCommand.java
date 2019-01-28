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

public class UpdUserFirstNamAdminCommand implements Command {
	
	public UpdUserFirstNamAdminCommand() {}

	// конструктор по умолчанию
	private String id;
	private String firstName;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Обновление фамилии пользователя
		
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		
		
		
		this.id = request.getParameter("id");
		this.firstName = request.getParameter("firstname");
		
		User user;
		String goToPage;
		
		ArrayList<User> arrUser = new ArrayList<User>();		
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		try {
			user = userService.adminupduserfirstnam(this.id, this.firstName);
			
			arrUser.add(user);
			
			if (user != null) {
				request.setAttribute("alluser", arrUser); // для передачи параметров на страницу
				goToPage = "/WEB-INF/jsp/Administrator.jsp";
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
