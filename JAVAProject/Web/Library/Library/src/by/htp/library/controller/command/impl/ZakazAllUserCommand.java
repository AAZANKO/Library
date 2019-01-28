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
import by.htp.library.entity.Zakaz;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.ZakazService;
import by.htp.library.service.exception.ServiceException;

public class ZakazAllUserCommand implements Command{
	
	public ZakazAllUserCommand() {}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// список заказов всех пользователей
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		
		ArrayList<Zakaz> arrZakaz;
		String goToPage;
		
		arrZakaz = null;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ZakazService userService = serviceFactory.getZakazService();
		
		try {
			
			arrZakaz = userService.allUsersZakazSearch();
			
			if(!(arrZakaz.isEmpty())) {
				request.setAttribute("message", "Список заказов");
				request.setAttribute("allzakaz", arrZakaz);
				goToPage="/WEB-INF/jsp/Administrator.jsp";
			}else {
				// request.setAttribute("errorMessage", "no such user");
				request.setAttribute("message", "Список заказов пуст");
				// goToPage="error.jsp";
				goToPage="/WEB-INF/jsp/Administrator.jsp";
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
