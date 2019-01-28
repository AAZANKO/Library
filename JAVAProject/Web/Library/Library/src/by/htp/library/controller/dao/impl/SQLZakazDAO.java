package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import by.htp.library.dao.ZakazDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Zakaz;

public class SQLZakazDAO implements ZakazDAO {
	
	public SQLZakazDAO() {}
	
	private int idUser;
	private int zakazId;
	private String userFirstName;
	private String userLastName;
	private String bookName;
	private String bookAutor;
	private String zakazDate;
	private String zakazSumm;
	private String zakazStatus;	
	
	private int nStartRows;
	private int nFinishRows;
	
	Connection myConn;
	Statement myStmt;
	ResultSet myRes;

	@Override
	public ArrayList<Zakaz> allZakaz() throws DAOException {
		// поиск всех заказов всех пользователей
		
		Zakaz zakaz;
		zakaz = null;
		
		ArrayList<Zakaz> arrListZakaz = new ArrayList<Zakaz>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select o.id_karzina, po.firstname, po.lastname, b.name, b.autor, o.datetime, b.summ, o.status " 
					+ " from opday.orders o " 
					+ " left outer join opday.karzina k on k.id_orders = o.id_karzina " 
					+ " left outer join opday.officer po on po.id = o.id_user " 
					+ " left outer join opday.book b on b.id = k.id_book order by 1");
			while (myRes.next()) {
				zakaz = new Zakaz();
				zakaz.setZakazId(Integer.parseInt(myRes.getString("id_karzina")));
				zakaz.setUserFirstName(myRes.getString("firstname"));
				zakaz.setUserLastName(myRes.getString("lastname"));
				zakaz.setBookName(myRes.getString("name"));
				zakaz.setBookAutor(myRes.getString("autor"));
				zakaz.setZakazDate(myRes.getString("datetime"));
				zakaz.setZakazSumm(myRes.getString("summ"));
				zakaz.setZakazStatus(myRes.getString("status"));
				arrListZakaz.add(zakaz);
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
		
		return arrListZakaz;
	}

	@Override
	public Integer countRowsTable() throws DAOException {
		// возвращает количество строк в таблице
		
				int nRows;
				nRows = 0;

				try {
					myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
					myStmt = myConn.createStatement();
					myRes = myStmt.executeQuery(
							"select count(o.id_karzina) as iid from opday.orders o");
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
	public ArrayList<Zakaz> allZakazSearchPageDAO(int nStartRows, int nFinishRows) throws DAOException {
		// возвращает спискок заказова 
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;
	
		Zakaz zakaz;
		zakaz = null;
		
		ArrayList<Zakaz> arrListZakaz = new ArrayList<Zakaz>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select * from "
					+ "(select rownum r, o.id_karzina, po.firstname, po.lastname, b.name, b.autor, o.datetime, b.summ, o.status " 
					+ " from opday.orders o " 
					+ " left outer join opday.karzina k on k.id_orders = o.id_karzina " 
					+ " left outer join opday.officer po on po.id = o.id_user " 
					+ " left outer join opday.book b on b.id = k.id_book order by 1) where r between " + this.nStartRows +	" and " + this.nFinishRows);
			while (myRes.next()) {
				zakaz = new Zakaz();
				zakaz.setZakazId(Integer.parseInt(myRes.getString("id_karzina")));
				zakaz.setUserFirstName(myRes.getString("firstname"));
				zakaz.setUserLastName(myRes.getString("lastname"));
				zakaz.setBookName(myRes.getString("name"));
				zakaz.setBookAutor(myRes.getString("autor"));
				zakaz.setZakazDate(myRes.getString("datetime"));
				zakaz.setZakazSumm(myRes.getString("summ"));
				zakaz.setZakazStatus(myRes.getString("status"));
				arrListZakaz.add(zakaz);
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
			
		return arrListZakaz;
	}

	@Override
	public ArrayList<Zakaz> einUserZakaz(String idUser) throws DAOException {
		// возвращает заказы текущего пользователя
		
		this.idUser = Integer.parseInt(idUser);
		
		Zakaz zakaz;
		zakaz = null;
		
		ArrayList<Zakaz> arrListZakaz = new ArrayList<Zakaz>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select o.id_karzina, po.firstname, po.lastname, b.name, b.autor, o.datetime, b.summ, o.status " 
					+ " from opday.orders o " 
					+ " left outer join opday.karzina k on k.id_orders = o.id_karzina " 
					+ " left outer join opday.officer po on po.id = o.id_user " 
					+ " left outer join opday.book b on b.id = k.id_book where o.id_user = " + this.idUser + " order by 1");
			while (myRes.next()) {
				zakaz = new Zakaz();
				zakaz.setZakazId(Integer.parseInt(myRes.getString("id_karzina")));
				zakaz.setUserFirstName(myRes.getString("firstname"));
				zakaz.setUserLastName(myRes.getString("lastname"));
				zakaz.setBookName(myRes.getString("name"));
				zakaz.setBookAutor(myRes.getString("autor"));
				zakaz.setZakazDate(myRes.getString("datetime"));
				zakaz.setZakazSumm(myRes.getString("summ"));
				zakaz.setZakazStatus(myRes.getString("status"));
				arrListZakaz.add(zakaz);
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
		
		return arrListZakaz;
	}

	@Override
	public ArrayList<Zakaz> einUserZakazSearchPageDAO(int nStartRows, int nFinishRows, String idUser)
			throws DAOException {
		// возвращает заказы текущего пользователя постранично
		
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;
	
		Zakaz zakaz;
		zakaz = null;
		
		ArrayList<Zakaz> arrListZakaz = new ArrayList<Zakaz>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select * from "
					+ "(select rownum r, o.id_karzina, po.firstname, po.lastname, b.name, b.autor, o.datetime, b.summ, o.status " 
					+ " from opday.orders o " 
					+ " left outer join opday.karzina k on k.id_orders = o.id_karzina " 
					+ " left outer join opday.officer po on po.id = o.id_user " 
					+ " left outer join opday.book b on b.id = k.id_book where o.id_user = " + this.idUser + " order by 1) "
					+ " where r between " + this.nStartRows +	" and " + this.nFinishRows);
			while (myRes.next()) {
				zakaz = new Zakaz();
				zakaz.setZakazId(Integer.parseInt(myRes.getString("id_karzina")));
				zakaz.setUserFirstName(myRes.getString("firstname"));
				zakaz.setUserLastName(myRes.getString("lastname"));
				zakaz.setBookName(myRes.getString("name"));
				zakaz.setBookAutor(myRes.getString("autor"));
				zakaz.setZakazDate(myRes.getString("datetime"));
				zakaz.setZakazSumm(myRes.getString("summ"));
				zakaz.setZakazStatus(myRes.getString("status"));
				arrListZakaz.add(zakaz);
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
			
		return arrListZakaz;
	}

	@Override
	public Integer countRowsTableForUser(String idUser) throws DAOException {
		// возвращает количество строк в таблице для юзера (юзер видит только свои заказы)
		
		this.idUser = Integer.parseInt(idUser);
		
		int nRows;
		nRows = 0;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery(
					"select count(o.id_karzina) as iid from opday.orders o where o.id_user = " + this.idUser);
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
}
