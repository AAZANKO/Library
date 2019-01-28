package by.htp.library.dao;

import by.htp.library.dao.impl.SQLBookDAO;
import by.htp.library.dao.impl.SQLUserDAO;
import by.htp.library.dao.impl.SQLZakazDAO;

public class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();
	
	private final UserDAO userDAO = new SQLUserDAO();
	private final BookDAO bookDAO = new SQLBookDAO();
	private final ZakazDAO zakazDAO = new SQLZakazDAO();

	private DAOFactory() {
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public BookDAO getBookDAO() {
		return bookDAO;
	}

	public ZakazDAO getZakazDAO() {
		return zakazDAO;
	}

	public static DAOFactory getInstance() {
		return instance;
	}

}