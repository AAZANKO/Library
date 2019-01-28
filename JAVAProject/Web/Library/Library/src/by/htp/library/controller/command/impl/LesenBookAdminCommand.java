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

public class LesenBookAdminCommand implements Command {

	public LesenBookAdminCommand() {
	}

	private String id;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// открытие книги на чтение

		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		
		
		this.id = request.getParameter("id");

		Book book;
		String goToPage;

		ArrayList<Book> arrBook = new ArrayList<Book>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		try {
			book = bookService.searchbookforlesen(this.id);
			arrBook.add(book);
			request.setAttribute("allbook", arrBook); 
			//request.setAttribute("mybook", book);
			goToPage = "/WEB-INF/jsp/AdminBookForLesenPage.jsp";
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
