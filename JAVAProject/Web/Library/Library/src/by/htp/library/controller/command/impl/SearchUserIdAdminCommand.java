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

public class SearchUserIdAdminCommand implements Command {

	public SearchUserIdAdminCommand() {
	}

	private String id;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// поиск пользователя по ID

		HttpSession session = request.getSession(true);
		User intId = (User) session.getAttribute("loginer");

		if (intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}

		this.id = request.getParameter("id");

		User user;
		String goToPage;

		// ArrayList<User> arrUser = new ArrayList<User>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		try {
			user = userService.searchuserid(this.id);
			// arrUser.add(user);

			if (user != null) {
				request.setAttribute("myuser", user); // для передачи параметров на страницу
				goToPage = "/WEB-INF/jsp/AdminUpdUserTable.jsp";
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
