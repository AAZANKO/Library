package by.htp.library.dao;

import java.util.ArrayList;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;
import by.htp.library.service.exception.ServiceException;

public interface BookDAO {
	
	ArrayList<Book> allBooks() throws DAOException;
	
	Book addEinBookAdmin(String nameBook, String autorBook, String dateBook, String priceBook, String saleBook, String ganre, String lesenBook) throws DAOException;
	
	Book searchbookid(String id) throws DAOException;
	
	Book adminediteinbookdao(String id, String nameBook, String autorBook, String dateBook, String priceBook, String saleBook, String ganreBook) throws DAOException;
	
	Book adminseachbookforlesendao(String id) throws DAOException;
	
	Book admindelbookdao(String id) throws DAOException;
	
	Book adminkaufbookdao(String id, String idUser) throws DAOException;
	
	ArrayList<Book> adminsearchbookautornamedao(String nameBook, String autorBook) throws DAOException;
	
	Integer countRowsTable() throws DAOException;
	
	ArrayList<Book> allBookSearchPageDAO(int nStartRows, int nFinishRows) throws DAOException;

}