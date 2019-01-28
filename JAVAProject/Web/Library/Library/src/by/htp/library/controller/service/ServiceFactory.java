package by.htp.library.service;

import by.htp.library.service.impl.BookServiceImpl;
import by.htp.library.service.impl.UserServiceImpl;
import by.htp.library.service.impl.ZakazServiceImpl;

public class ServiceFactory {
	
	private static final ServiceFactory instance = new ServiceFactory();

	private final UserService userService = new UserServiceImpl();
	private final BookService bookService = new BookServiceImpl();
	private final ZakazService zakazService = new ZakazServiceImpl();

	private ServiceFactory() {
	}

	public UserService getUserService() {
		return userService;
	}

	public BookService getBookService() {
		return bookService;
	}
	
	public ZakazService getZakazService() {
		return zakazService;
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

}
