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

public class AllBookSearchPageCommand implements Command{
	
	public AllBookSearchPageCommand() {}
	
	private String sIdPage; // ид пришедший параметр
	private int idPage;	// ид для расчета
	private int nRowsTable; // количество строк в таблице
	
	private final int NROWSEINPAGE = 10;
	private int nBeginRows; // с какой строки начинать
	
	private int nStartRows = 0; // вычисление с какой строки делать выборку
	private int nFinishRows = 0; // вычисление до какой строки делать выборку
	private int nButton = 0; // вычисление сколько кнопок навигации будет на странице
	private int nButtonFOR; // для цикла т.к в цикле начало нумерации с 0
	
	// @SuppressWarnings("null")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// поиск книг с пагенацией
		
		HttpSession session = request.getSession(true);
		User intId = (User)session.getAttribute("loginer");
				
		if(intId == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}		
		
		this.sIdPage = request.getParameter("idPage");

		if (this.sIdPage == null || this.sIdPage.isEmpty()) {
			idPage = 1;
		}else {
			idPage = Integer.parseInt(this.sIdPage);
		}		
		
		String goToPage;
		
		ArrayList<Book> arrBook;
		arrBook = null;
		
		ArrayList<Integer> arrIntButton = new ArrayList<Integer>();
		
		nBeginRows = NROWSEINPAGE - 1; // вычисление разности между первой и последней строками
					
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();
		
		try {
			
			nRowsTable = bookService.countRowsBookTable(); // возвращает кол-во строк в таблице
			nButton = (nRowsTable / NROWSEINPAGE) + 1; // определяем количество кнопок перехода + Prev + Next
			nFinishRows = idPage * NROWSEINPAGE; // вычисляем rownum строки последней входящей в выборку
			nStartRows = nFinishRows - nBeginRows; // вычисляем rownum строки первой входящей в выборку
			// построение кнопок на странице-----------------------------------------------------------
			nButtonFOR = nButton + 1; // на один больше т.к. оператор FOR
			for (int i = 1; i < nButtonFOR; i++) {
				arrIntButton.add(i);
			}
			//  нужно вернуть параметры с какой по какйю страницу выводить
			// arrBook = bookService.allBookSearch();
			arrBook = bookService.allBookSearchPage(nStartRows, nFinishRows);
			
			if(arrBook != null) {
				request.setAttribute("message", "Список книг");
				request.setAttribute("arrIntButton", arrIntButton);
				request.setAttribute("allbook", arrBook);
				//goToPage="/WEB-INF/jsp/Administrator.jsp";
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
