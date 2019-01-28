package by.htp.library.service;

import java.util.ArrayList;

import by.htp.library.entity.Book;
import by.htp.library.service.exception.ServiceException;

public interface BookService {
		
	ArrayList<Book> allBookSearch() throws ServiceException;
	
	Book adminaddeinbook(String nameBook, String autorBook, String dateBook, String priceBook, String saleBook, String ganreBook, String lesenBook)
			throws ServiceException;
		
	Book searchbookid(String id) throws ServiceException;
	
	Book adminediteinbook(String id, String nameBook, String autorBook, String dateBook, String priceBook, String saleBook, String ganreBook) throws ServiceException;
	
	Book searchbookforlesen(String id) throws ServiceException;
	
	Book admindelbook(String id) throws ServiceException;
	
	Book kaufeinbookadmin(String id, String idUser) throws ServiceException;
		
	ArrayList<Book> searchBookAutorName(String autorBook, String nameBook) throws ServiceException;
	
	Integer countRowsBookTable() throws ServiceException;
	
	ArrayList<Book> allBookSearchPage(int nStartRows, int nFinishRows) throws ServiceException;
	
}
