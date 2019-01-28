package by.htp.library.controller.command.impl;

import java.io.IOException;

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

public class LoginationCommand implements Command {
	
	public LoginationCommand() {}

	private String login;
	private String password;	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		this.login = request.getParameter("login");
		this.password = request.getParameter("password");	
		
		// Хотел обнулить параметры - но что-то пошло не так.
//		login = null;
//		password = null;
		
		//User user = new User();
		User user;
		String goToPage;
		
		/////////////////////////////////////////////////
		// новый сеанс
		// HttpSession session = request.getSession(true);

		// сахранить заначение переменной в текущей сессии
		// session.setAttribute("myuser", user);

		// прочитать данные сессии
		// Integer intId = (Integer)session.getAttribute("myuser");

		// завершение сеанса
		// session.invalidate();

		// удаление сеанса
		// session.removeAttribute("myuser");

		
		// если вернулось значение то одна страница
		// если вернулся null или Exception то другая страница Error.jsp
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		try {
			
			user = userService.logination(this.login, this.password);
			
			if(user != null) {
				request.setAttribute("myuser", user); // для передачи параметров на страницу
				request.getSession(true).setAttribute("loginer", user);
			
				if(user.getRole() == 1) {
					goToPage="/WEB-INF/jsp/Administrator.jsp";
				}else {
					goToPage="/WEB-INF/jsp/Users.jsp";
				}	
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
