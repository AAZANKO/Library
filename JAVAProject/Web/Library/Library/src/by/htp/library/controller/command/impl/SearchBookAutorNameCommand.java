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

public class SearchBookAutorNameCommand implements Command{
	
	public SearchBookAutorNameCommand() {}
	
	private String nameBook;
	private String autorBook;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// поиск книги по наименованию и по автору
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		
		this.nameBook = request.getParameter("namebook");
		this.autorBook = request.getParameter("autorbook");
		
		Book book;
		String goToPage;
		
		ArrayList<Book> arrBook = new ArrayList<Book>();
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		try {
			arrBook = bookService.searchBookAutorName(this.autorBook, this.nameBook);
			//arrBook.add(book);
			
			if (!(arrBook.isEmpty())) {
				// request.setAttribute("myuser", user); // для передачи параметров на страницу
				request.setAttribute("allbook", arrBook);
				request.setAttribute("message", "Список книг");
				// goToPage = "/WEB-INF/jsp/Administrator.jsp";
				if(intId.getRole() == 1) {
					goToPage="/WEB-INF/jsp/Administrator.jsp";
				}else {
					goToPage="/WEB-INF/jsp/Users.jsp";
				}
			} else {
				request.setAttribute("message", "Книг не найдено");
				goToPage = "/WEB-INF/jsp/Administrator.jsp";
				// goToPage = "error.jsp";
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
