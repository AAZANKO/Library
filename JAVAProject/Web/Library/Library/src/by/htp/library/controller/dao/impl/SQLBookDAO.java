package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import by.htp.library.dao.BookDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;

public class SQLBookDAO implements BookDAO {

	public SQLBookDAO() {
	}

	private int idUser;

	private int id;
	private String nameBook;
	private String autorBook;
	private String dateBook;
	private int priceBook;
	private int saleBook;
	private String ganreBook;
	private String lesenBook;

	private int nStartRows;
	private int nFinishRows;
	
	Connection myConn;
	Statement myStmt;
	ResultSet myRes;

	@Override
	public Book addEinBookAdmin(String nameBook, String autorBook, String dateBook, String priceBook, String saleBook,
			String ganreBook, String lesenBook) throws DAOException {
		// добавление новой книги в базу

		this.nameBook = nameBook;
		this.autorBook = autorBook;
		this.dateBook = dateBook;
		this.priceBook = Integer.parseInt(priceBook);
		this.saleBook = Integer.parseInt(saleBook);
		this.ganreBook = ganreBook;
		this.lesenBook = lesenBook;
		int iid;
		iid = 0;

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select id, autor, name, datebook, summ, sale, genre, lesen from opday.book b where upper(b.name) like upper('"
							+ this.nameBook + "')");
			while (myRes.next()) {
				if (myRes.getString("name") == this.nameBook) {
					return null;
				}
			}

			String sql = "insert into opday.book (id, autor, name, datebook, summ, sale, genre, lesen) "
					+ "values (opday.sq_book.nextval, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = myConn.prepareStatement(sql);
			ps.setString(1, this.autorBook);
			ps.setString(2, this.nameBook);
			ps.setString(3, this.dateBook);
			ps.setLong(4, this.priceBook);
			ps.setLong(5, this.saleBook);
			ps.setString(6, this.ganreBook);
			ps.setString(7, this.lesenBook);
			ps.executeUpdate();

			myRes = myStmt.executeQuery(
					"select id, autor, name, datebook, summ, sale, genre, lesen from opday.book b where upper(b.name) like upper('"
							+ this.nameBook + "')");

			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				// book.setLesenBook(myRes.getString("lesen"));
				// arrayListBook.add(book);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return book;
	}

	@Override
	public ArrayList<Book> allBooks() throws DAOException {
		// поиск всех книг и возврат списка

		Book book;
		book = null;

		ArrayList<Book> arrayListBook = new ArrayList<Book>();
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select b.id, b.autor, b.name, b.datebook, b.summ, b.sale, b.genre, b.lesen from opday.book b order by 1");
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				// book.setLesenBook(myRes.getString("lesen"));
				arrayListBook.add(book);
				// id, autor, name, datebook, summ, sale, genre, lesen
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return arrayListBook;
	}

	@Override
	public Book searchbookid(String id) throws DAOException {
		// поиск книги по ид
		this.id = Integer.parseInt(id);

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select b.id, b.autor, b.name, b.datebook, b.summ, b.sale, b.genre, b.lesen from opday.book b where b.id ="
							+ this.id);
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				// book.setLesenBook(myRes.getString("lesen"));
				// arrayListBook.add(book);
				// id, autor, name, datebook, summ, sale, genre, lesen
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return book;
	}

	@Override
	public Book adminediteinbookdao(String id, String nameBook, String autorBook, String dateBook, String priceBook,
			String saleBook, String ganreBook) throws DAOException {
		// сохранение изменений книги

		this.id = Integer.parseInt(id);
		this.nameBook = nameBook;
		this.autorBook = autorBook;
		this.dateBook = dateBook;
		this.priceBook = Integer.parseInt(priceBook);
		this.saleBook = Integer.parseInt(saleBook);
		this.ganreBook = ganreBook;

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();

			String sql = "update opday.book b set b.summ = ?, b.sale = ? where b.id = ?";
			PreparedStatement ps = myConn.prepareStatement(sql);
			ps.setLong(1, this.priceBook);
			ps.setLong(2, this.saleBook);
			ps.setLong(3, this.id);
			ps.executeUpdate();

			myRes = myStmt.executeQuery(
					"select id, autor, name, datebook, summ, sale, genre, lesen from opday.book b where b.id = "
							+ this.id);
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				// book.setLesenBook(myRes.getString("lesen"));
				// arrayListBook.add(book);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return book;
	}

	@Override
	public Book adminseachbookforlesendao(String id) throws DAOException {
		// возвращение краткого содержания книги

		this.id = Integer.parseInt(id);

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select id, autor, name, datebook, summ, sale, genre, lesen from opday.book b where b.id = "
							+ this.id);
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				book.setLesenBook(myRes.getString("lesen"));
				// arrayListBook.add(book);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}

		return book;
	}

	@Override
	public Book admindelbookdao(String id) throws DAOException {
		// удаление книги из базы

		this.id = Integer.parseInt(id);

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select b.id, b.autor, b.name, b.datebook, b.summ, b.sale, b.genre, b.lesen from opday.book b where b.id = "
							+ this.id);
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				book.setLesenBook(myRes.getString("lesen"));
			}

			myRes = myStmt.executeQuery("select b.id from opday.book b where b.id = " + this.id);

			while (myRes.next()) {
				if (Integer.parseInt(myRes.getString("id")) == this.id) {
					myRes = myStmt.executeQuery("select k.id_book from opday.karzina k where k.id_book = " + this.id
							+ " group by k.id_book");
					if (myRes.next()) {
						while (!(myRes.next())) {
							if (Integer.parseInt(myRes.getString("id_book")) == this.id) {
								return null;
							} else {
								myStmt.executeUpdate("delete from opday.book b where b.id = " + this.id);
							}
						}
					} else {
						myStmt.executeUpdate("delete from opday.book b where b.id = " + this.id);
					}
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}

		return book;

	}

	@Override
	public Book adminkaufbookdao(String id, String idUser) throws DAOException {
		// покупка книги

		// вот тут и сказочки конец а кто слушал
		// как узнать какой пользователь купил книгу ?
		// взять из сеанса, но как положить пользователя в сеанс ?

		this.id = Integer.parseInt(id);
		this.idUser = Integer.parseInt(idUser);

		int idOrders;
		idOrders = 0;

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select opday.sq_karzina_idord.nextval from dual");
			while (myRes.next()) {
				idOrders = Integer.parseInt(myRes.getString("nextval"));
			}

			String sqlKaz = "insert into opday.karzina k (k.id, k.id_orders, k.id_book) values (opday.sq_karzina.nextval, ?, ?)";
			PreparedStatement psKaz = myConn.prepareStatement(sqlKaz);
			psKaz.setLong(1, idOrders);
			psKaz.setLong(2, this.id);
			psKaz.executeUpdate();

			String sqlOrd = "insert into opday.orders o (o.id, o.id_user, o.id_karzina, o.datetime, o.status) values (opday.sq_orders.nextval, ?, ?, TO_CHAR(SYSDATE, 'dd.mm.yyyy hh24:mi:ss'), ?)";
			PreparedStatement psOrd = myConn.prepareStatement(sqlOrd);
			psOrd.setLong(1, this.idUser);
			psOrd.setLong(2, idOrders);
			psOrd.setLong(3, 1);
			psOrd.executeUpdate();

			myRes = myStmt.executeQuery(
					"select id, autor, name, datebook, summ, sale, genre, lesen from opday.book b where b.id = "
							+ this.id);
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				book.setLesenBook(myRes.getString("lesen"));
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}

		return book;

	}

	@Override
	public ArrayList<Book> adminsearchbookautornamedao(String nameBook, String autorBook) throws DAOException {
		// поиск книги по автору и наименованию

		this.nameBook = nameBook;
		this.autorBook = autorBook;

		ArrayList<Book> arrayListBook = new ArrayList<Book>();

		Book book;
		book = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select b.id, b.autor, b.name, b.datebook, b.summ, b.sale, b.genre, b.lesen "
					+ " from opday.book b " + " where upper(b.autor) like upper('%" + this.autorBook
					+ "%') and upper(b.name) like upper('%" + this.nameBook + "%') order by 1");
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				book.setLesenBook(myRes.getString("lesen"));
				arrayListBook.add(book);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return arrayListBook;
	}

	@Override
	public Integer countRowsTable() throws DAOException {
		// возвращает количество строк в таблице

		int nRows;
		nRows = 0;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select count(b.id) as iid from opday.book b");

			while (myRes.next()) {
				nRows = Integer.parseInt(myRes.getString("iid"));
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}

		return nRows;
	}

	
	
	@Override
	public ArrayList<Book> allBookSearchPageDAO(int nStartRows, int nFinishRows) throws DAOException {
		// возвращение списка согасно первой и последней строчке
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;
		
		Book book;
		book = null;

		ArrayList<Book> arrayListBook = new ArrayList<Book>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select * from (select rownum r, id, autor, name, datebook, summ, sale, genre, lesen from opday.book b order by id) "
					+ "where r between " + this.nStartRows +	" and " + this.nFinishRows);
			while (myRes.next()) {
				book = new Book();
				book.setId(Integer.parseInt(myRes.getString("id")));
				book.setNameBook(myRes.getString("name"));
				book.setAutorBook(myRes.getString("autor"));
				book.setDateBook(myRes.getString("datebook"));
				book.setPriceBook(myRes.getString("summ"));
				book.setSaleBook(myRes.getString("sale"));
				book.setGanreBook(myRes.getString("genre"));
				// book.setLesenBook(myRes.getString("lesen"));
				arrayListBook.add(book);
				// id, autor, name, datebook, summ, sale, genre, lesen
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DAOException("smth happended", e);
		} finally {
			// не забыть закрыть то что открывал !!!!!!!!!!!!!
			try {
				if (myStmt != null) {
					myStmt.close();
				}
				if (myRes != null) {
					myRes.close();
				}
				if (myConn != null) {
					myConn.close();
				}
			} catch (SQLException e) {
				throw new DAOException("smth happended", e);
			}
		}
		return arrayListBook;
	}

}
