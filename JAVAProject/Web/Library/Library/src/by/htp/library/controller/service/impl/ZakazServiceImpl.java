package by.htp.library.service.impl;

import java.util.ArrayList;

import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.ZakazDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Zakaz;
import by.htp.library.service.ZakazService;
import by.htp.library.service.exception.ServiceException;

public class ZakazServiceImpl implements ZakazService{
	
	public ZakazServiceImpl() {}
	
	private String idUser;
	private int zakazId;
	private String userFirstName;
	private String userLastName;
	private String bookName;
	private String bookAutor;
	private String zakazDate;
	private String zakazStatus;
	
	private int nStartRows;
	private int nFinishRows;
	
	@Override
	public ArrayList<Zakaz> allUsersZakazSearch() throws ServiceException {
		//  поиск всех заказов в библиотеке
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		ZakazDAO zakazDAO = daoFactory.getZakazDAO();
		
		ArrayList<Zakaz> arrZakaz;
		
		try {
			arrZakaz = zakazDAO.allZakaz();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrZakaz;
	}

	@Override
	public Integer countRowsZakazTable() throws ServiceException {
		// возвращает количество строк втабице
		
		int countRowsTableZakaz;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		ZakazDAO zakazDAO = daoFactory.getZakazDAO();

		try {
			countRowsTableZakaz = zakazDAO.countRowsTable();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return countRowsTableZakaz;
	}

	@Override
	public ArrayList<Zakaz> allZakazSearchPage(int nStartRows, int nFinishRows) throws ServiceException {
		// возвращает 

		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ZakazDAO zakazDAO = daoFactory.getZakazDAO();

		ArrayList<Zakaz> arrZakaz = new ArrayList<Zakaz>();

		try {
			arrZakaz = zakazDAO.allZakazSearchPageDAO(this.nStartRows, this.nFinishRows);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrZakaz;
	}

	@Override
	public ArrayList<Zakaz> einUsersZakazSearch(String idUser) throws ServiceException {
		// возвращает список заказов текущего пользователя
		
		this.idUser = idUser;
		
		if(this.idUser == null || this.idUser.isEmpty()) {
			return null;
		}
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		ZakazDAO zakazDAO = daoFactory.getZakazDAO();
		
		ArrayList<Zakaz> arrZakaz;
		
		try {
			arrZakaz = zakazDAO.einUserZakaz(this.idUser);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrZakaz;
	}

	@Override
	public ArrayList<Zakaz> einUserZakazSearchPage(int nStartRows, int nFinishRows, String idUser)
			throws ServiceException {
		// возвращает список заказов текущего пользователя постранично
		
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;
		this.idUser = idUser;
		
		if(this.idUser == null || this.idUser.isEmpty()) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		ZakazDAO zakazDAO = daoFactory.getZakazDAO();

		ArrayList<Zakaz> arrZakaz = new ArrayList<Zakaz>();

		try {
			arrZakaz = zakazDAO.einUserZakazSearchPageDAO(this.nStartRows, this.nFinishRows, this.idUser);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrZakaz;
	}

	@Override
	public Integer countRowsZakazTableForUser(String idUser) throws ServiceException {
		// возвращает количество строк для юзера (юзер видит только свои заказы)
		
		this.idUser = idUser;

		if(this.idUser == null || this.idUser.isEmpty()) {
			return null;
		}
		
		int countRowsTableZakaz;

		DAOFactory daoFactory = DAOFactory.getInstance();
		ZakazDAO zakazDAO = daoFactory.getZakazDAO();

		try {
			countRowsTableZakaz = zakazDAO.countRowsTableForUser(this.idUser);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return countRowsTableZakaz;
	}

}
