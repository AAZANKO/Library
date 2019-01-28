package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.controller.command.Command;

import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.exception.ServiceException;


public class AllBookSearchCommand implements Command{
	
	public AllBookSearchCommand() {}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// поиск всех книг
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}		
		
		ArrayList<Book> arrBook;
		String goToPage;
		arrBook = null;		
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();
		
		try {
			
			arrBook = bookService.allBookSearch();
			
			if(arrBook != null) {
				request.setAttribute("message", "Список книг");
				request.setAttribute("allbook", arrBook);
			
				if(intId.getRole() == 1) {
					goToPage="/WEB-INF/jsp/Administrator.jsp";
				}else {
					goToPage="/WEB-INF/jsp/Users.jsp";
				}	
			}else {
				// выводить норм сообщения
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
