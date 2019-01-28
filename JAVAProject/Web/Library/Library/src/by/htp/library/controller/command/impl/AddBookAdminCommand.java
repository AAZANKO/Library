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

public class AddBookAdminCommand implements Command {

	public AddBookAdminCommand() {}
	
	private int id;
	private String nameBook;
	private String autorBook;
	private String dateBook;
	private String priceBook;
	private String saleBook;
	private String ganreBook;
	private String lesenBook;

	private String s; 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// добавление книги в базу

		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}		
		
		this.nameBook = request.getParameter("namebook");
		this.autorBook = request.getParameter("autorbook");
		this.dateBook = request.getParameter("datebook");
		this.priceBook = request.getParameter("summbook");
		this.saleBook = request.getParameter("salebook");
		this.ganreBook = request.getParameter("genrebook");
		s = request.getParameter("lesenbook");

		if (s.length() > 2500) {
			this.lesenBook = s.substring(0, 2500);
		} else {
			this.lesenBook = s;
		}		
		
		Book book;
		String goToPage;

		ArrayList<Book> arrBook = new ArrayList<Book>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		try {
			book = bookService.adminaddeinbook(this.nameBook, this.autorBook, this.dateBook, this.priceBook,
					this.saleBook, this.ganreBook, this.lesenBook);
			arrBook.add(book);
			if (book != null) {
				// request.setAttribute("myuser", user); // для передачи параметров на страницу
				request.setAttribute("allbook", arrBook);
				request.setAttribute("message", "Книга добавлена");
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
