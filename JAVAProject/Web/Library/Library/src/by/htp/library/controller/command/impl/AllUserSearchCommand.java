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

public class AllUserSearchCommand implements Command {
	
	public AllUserSearchCommand() {}
	
	private String goToPage;
	private String id_user;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// поиск всех пользователей

		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
			
//		this.id_user = request.getParameter("id_user");
//		if(this.id_user == null || this.id_user.isEmpty()) {
//			System.out.println("this.id_user == null || this.id_user.isEmpty()");
//			goToPage="index.jsp";
//		}else {
//			System.out.println("this.id_user == " + this.id_user +" || this.id_user.isEmpty()");
//		}
		
		ArrayList<User> arrUser;
		arrUser = null;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		try {
			
			arrUser = userService.allUsersSearch();
			
			if(arrUser != null) {
				request.setAttribute("message", "Список пользователей");
				request.setAttribute("alluser", arrUser);
				goToPage="/WEB-INF/jsp/Administrator.jsp";
			}else {
				request.setAttribute("errorMessage", "no such user");
				goToPage="error.jsp";
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
