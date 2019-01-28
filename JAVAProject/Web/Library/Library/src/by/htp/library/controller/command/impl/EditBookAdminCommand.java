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

public class EditBookAdminCommand implements Command {

	public EditBookAdminCommand() {}
	
	private String id;
	private String nameBook;
	private String autorBook;
	private String dateBook;
	private String priceBook;
	private String saleBook;
	private String ganreBook;
	private String lesenBook;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// изменение книги
	
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}	
		
		
		this.id = request.getParameter("id");
		this.nameBook = request.getParameter("namebook");
		this.autorBook = request.getParameter("autorbook");
		this.dateBook = request.getParameter("datebook");
		this.priceBook = request.getParameter("summbook");
		this.saleBook = request.getParameter("salebook");
		this.ganreBook = request.getParameter("genrebook");
		if(this.saleBook == null || this.saleBook.isEmpty()) {
			this.saleBook = "0";
		}
		
		Book book;
		String goToPage;

		ArrayList<Book> arrBook = new ArrayList<Book>();

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();
		// adminupduserfirstnam
		try {
			book = bookService.adminediteinbook(this.id, this.nameBook, this.autorBook, this.dateBook, this.priceBook,
					this.saleBook, this.ganreBook);
			arrBook.add(book);
			if (book != null) {
				request.setAttribute("allbook", arrBook);
				request.setAttribute("message", "Книга обновлена!");
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
