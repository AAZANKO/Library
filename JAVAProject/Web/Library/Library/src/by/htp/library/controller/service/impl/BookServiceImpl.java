package by.htp.library.service.impl;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.library.dao.BookDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.service.BookService;
import by.htp.library.service.exception.ServiceException;

public class BookServiceImpl implements BookService {
	
	public BookServiceImpl() {}

	private String idUser;
	
	private String id;
	private String nameBook;
	private String autorBook;
	private String dateBook;
	private String priceBook;
	private String saleBook;
	private String ganreBook;
	private String lesenBook;

	private Matcher mId;
	private Matcher mNameBook;
	private Matcher mAutorBook;
	private Matcher mDateBook;
	private Matcher mPriceBook;
	private Matcher mSaleBook;
	private Matcher mGanreBook;
	private Matcher mLesenBook;

	private boolean bId;
	private boolean bNameBook;
	private boolean bAutorBook;
	private boolean bDateBook;
	private boolean bPriceBook;
	private boolean bSaleBook;
	private boolean bGanreBook;
	private boolean bLesenBook;
	
	private int nStartRows;
	private int nFinishRows;
	
	private int nButtonFOR;

	private Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]");

	@Override
	public Book adminaddeinbook(String nameBook, String autorBook, String dateBook, String priceBook, String saleBook,
			String ganreBook, String lesenBook) throws ServiceException {
		// добавление в базу одной книги

		this.nameBook = nameBook.trim();
		this.autorBook = autorBook.trim();
		this.dateBook = dateBook.trim();
		this.priceBook = priceBook.trim();
		this.saleBook = saleBook.trim();
		this.ganreBook = ganreBook.trim();
		this.lesenBook = lesenBook.trim();

		if (this.nameBook == null || this.nameBook.isEmpty()) {
			return null;
		}
		if (this.autorBook == null || this.autorBook.isEmpty()) {
			return null;
		}
		if (this.dateBook == null || this.dateBook.isEmpty()) {
			return null;
		}
		if (this.priceBook == null || this.priceBook.isEmpty()) {
			return null;
		}
		if (this.saleBook == null || this.saleBook.isEmpty()) {
			return null;
		}
		if (this.ganreBook == null || this.ganreBook.isEmpty()) {
			return null;
		}
		if (this.lesenBook == null || this.lesenBook.isEmpty()) {
			return null;
		}

		mNameBook = p.matcher(this.nameBook);
		mAutorBook = p.matcher(this.autorBook);
		mDateBook = p.matcher(this.dateBook);
		mPriceBook = p.matcher(this.priceBook);
		mSaleBook = p.matcher(this.saleBook);
		mGanreBook = p.matcher(this.ganreBook);
		mLesenBook = p.matcher(this.lesenBook);

		bNameBook = mNameBook.find();
		bAutorBook = mAutorBook.find();
		bDateBook = mDateBook.find();
		bPriceBook = mPriceBook.find();
		bSaleBook = mSaleBook.find();
		bGanreBook = mGanreBook.find();
		bLesenBook = mLesenBook.find();

		if (bNameBook) {
			return null;
		}
		if (bAutorBook) {
			return null;
		}
		if (bDateBook) {
			return null;
		}
		if (bPriceBook) {
			return null;
		}
		if (bSaleBook) {
			return null;
		}
		if (bGanreBook) {
			return null;
		}
		if (bLesenBook) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		Book book;

		try {
			book = bookDAO.addEinBookAdmin(this.nameBook, this.autorBook, this.dateBook, this.priceBook, this.saleBook,
					this.ganreBook, this.lesenBook);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return book;
	}

	@Override
	public ArrayList<Book> allBookSearch() throws ServiceException {
		// поиск всех книг

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		ArrayList<Book> arrBook;

		try {
			arrBook = bookDAO.allBooks();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrBook;

	}

	@Override
	public Book searchbookid(String id) throws ServiceException {
		// поиск книги по ид

		this.id = id.trim();

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		Book book;

		try {
			book = bookDAO.searchbookid(this.id);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return book;
	}

	@Override
	public Book adminediteinbook(String id, String nameBook, String autorBook, String dateBook, String priceBook,
			String saleBook, String ganreBook) throws ServiceException {
		// сохранение изменений отредактированной книги

		this.id = id.trim();
		this.nameBook = nameBook.trim();
		this.autorBook = autorBook.trim();
		this.dateBook = dateBook.trim();
		this.priceBook = priceBook.trim();
		this.saleBook = saleBook.trim();
		this.ganreBook = ganreBook.trim();
		// this.lesenBook = lesenBook.trim();

		if (this.id == null || this.id.isEmpty()) {
			return null;
		}
		if (this.nameBook == null || this.nameBook.isEmpty()) {
			return null;
		}
		if (this.autorBook == null || this.autorBook.isEmpty()) {
			return null;
		}
		if (this.dateBook == null || this.dateBook.isEmpty()) {
			return null;
		}
		if (this.priceBook == null || this.priceBook.isEmpty()) {
			return null;
		}
		// if (this.saleBook == null || this.saleBook.isEmpty()) {
		// return null;
		// }
		if (this.ganreBook == null || this.ganreBook.isEmpty()) {
			return null;
		}

		mNameBook = p.matcher(this.nameBook);
		mAutorBook = p.matcher(this.autorBook);
		mDateBook = p.matcher(this.dateBook);
		mPriceBook = p.matcher(this.priceBook);
		mSaleBook = p.matcher(this.saleBook);
		mGanreBook = p.matcher(this.ganreBook);
		// mLesenBook = p.matcher(this.lesenBook);

		bNameBook = mNameBook.find();
		bAutorBook = mAutorBook.find();
		bDateBook = mDateBook.find();
		bPriceBook = mPriceBook.find();
		bSaleBook = mSaleBook.find();
		bGanreBook = mGanreBook.find();
		// bLesenBook = mLesenBook.find();

		if (bNameBook) {
			return null;
		}
		if (bAutorBook) {
			return null;
		}
		if (bDateBook) {
			return null;
		}
		if (bPriceBook) {
			return null;
		}
		if (bSaleBook) {
			return null;
		}
		if (bGanreBook) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		Book book;

		try {
			book = bookDAO.adminediteinbookdao(this.id, this.nameBook, this.autorBook, this.dateBook, this.priceBook,
					this.saleBook, this.ganreBook);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return book;

	}

	@Override
	public Book searchbookforlesen(String id) throws ServiceException {
		// возвращает краткое содержание книги

		this.id = id.trim();

		if (this.id == null || this.id.isEmpty()) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		Book book;

		try {
			book = bookDAO.adminseachbookforlesendao(this.id);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return book;
	}

	@Override
	public Book admindelbook(String id) throws ServiceException {
		// удаление книги из таблицы

		this.id = id.trim();

		if (this.id == null || this.id.isEmpty()) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		Book book;

		try {
			book = bookDAO.admindelbookdao(this.id);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return book;
	}

	
	
	@Override
	public Book kaufeinbookadmin(String id, String idUser) throws ServiceException {
		// покупка книги
		
		this.id = id.trim();
		this.idUser = idUser.trim();

		if (this.id == null || this.id.isEmpty()) {
			return null;
		}
		if (this.idUser == null || this.idUser.isEmpty()) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		Book book;

		try {
			book = bookDAO.adminkaufbookdao(this.id, this.idUser);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return book;
	}

	@Override
	public ArrayList<Book> searchBookAutorName(String autorBook, String nameBook) throws ServiceException {
		// поиск книг по автору и по наименованию
		
		this.nameBook = nameBook.trim();
		this.autorBook = autorBook.trim();

//		if (this.nameBook == null || this.nameBook.isEmpty()) {
//			return null;
//		}
//		if (this.autorBook == null || this.autorBook.isEmpty()) {
//			return null;
//		}
		
		mNameBook = p.matcher(this.nameBook);
		mAutorBook = p.matcher(this.autorBook);
		
		bNameBook = mNameBook.find();
		bAutorBook = mAutorBook.find();
		
		if (bNameBook) {
			return null;
		}
		if (bAutorBook) {
			return null;
		}
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		ArrayList<Book> arrBook = new ArrayList<Book>();

		try {
			arrBook = bookDAO.adminsearchbookautornamedao(this.nameBook, this.autorBook);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrBook;
	}

	
	
	@Override
	public Integer countRowsBookTable() throws ServiceException {
		// подсчет количества строк в таблице
		
		int countRowsTableBook;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		try {
			countRowsTableBook = bookDAO.countRowsTable();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return countRowsTableBook;
	}

	
	
	
	@Override
	public ArrayList<Book> allBookSearchPage(int nStartRows, int nFinishRows) throws ServiceException {
		// возвращает список книг согасно параметрам
		
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;
		
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();

		ArrayList<Book> arrBook = new ArrayList<Book>();

		try {
			arrBook = bookDAO.allBookSearchPageDAO(this.nStartRows, this.nFinishRows);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrBook;
	}


	
}
