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

public class ZakazEinUserCommand implements Command{
	
	public ZakazEinUserCommand() {}

	private String idUser;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// все заказы одного пользователя
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		
		this.idUser = request.getParameter("id_user");
		
		ArrayList<Zakaz> arrZakaz;
		String goToPage;
		
		arrZakaz = null;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ZakazService userService = serviceFactory.getZakazService();
		
		try {
			
			arrZakaz = userService.einUsersZakazSearch(this.idUser);
			
			if(!(arrZakaz.isEmpty())) {
				request.setAttribute("message", "Список заказов");
				request.setAttribute("allzakaz", arrZakaz);
				//goToPage="/WEB-INF/jsp/Administrator.jsp";
				if(intId.getRole() == 1) {
					goToPage="/WEB-INF/jsp/Administrator.jsp";
				}else {
					goToPage="/WEB-INF/jsp/Users.jsp";
				}					
			}else {
				// request.setAttribute("errorMessage", "no such user");
				request.setAttribute("message", "Список заказов пуст");
				// goToPage="error.jsp";
				//goToPage="/WEB-INF/jsp/Administrator.jsp";
				if(intId.getRole() == 1) {
					goToPage="/WEB-INF/jsp/Administrator.jsp";
				}else {
					goToPage="/WEB-INF/jsp/Users.jsp";
				}	
				
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
