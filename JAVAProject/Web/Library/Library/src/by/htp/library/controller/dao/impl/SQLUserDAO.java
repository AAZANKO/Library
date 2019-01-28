package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.User;

public class SQLUserDAO implements UserDAO {

	public SQLUserDAO() {
	}
	private int id;
	private String firstName;
	private String lastName;
	private String midleName;
	private String login;
	private String password; // не хранить в объекте
	private String eMail;
	private int role;
	
	private int nStartRows;
	private int nFinishRows;
	
	Connection myConn;
	Statement myStmt;
	ResultSet myRes;

	@Override
	public User logination(String login, String password) throws DAOException {

		this.login = login;
		this.password = password;
		User user;
		user = null;

		try {
			// Class.forName("oracle.jdbc.driver.OracleDriver"); // перенесено на index.jsp
			// myConn =
			// DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Library",
			// "administr", "administr");
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);

			myStmt = myConn.createStatement();

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where upper(o.login) = upper('" + this.login + "') and o.password =" + this.password);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
				// arrayListUser.add(user);
			}
			/*
			 * { respString = myRes.getString("id") + ";" + myRes.getString("firstname") +
			 * ";" + myRes.getString("lastname") + ";" + myRes.getString("midlename") + ";"
			 * + myRes.getString("dateopen") + ";" + myRes.getString("dateclose") + ";" +
			 * myRes.getString("login") + ";" + myRes.getString("password") + ";" +
			 * myRes.getString("email"); }
			 */
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

		return user;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User registration(String firstname, String lastname, String midleName, String login, String password,
			String eMail) throws DAOException {

		// регистрация
		this.firstName = firstname.toUpperCase();
		this.lastName = lastname.toUpperCase();
		this.midleName = midleName.toUpperCase();
		this.login = login.toUpperCase();
		this.password = password;
		this.eMail = eMail;
		int iid;
		iid = 0;

		User user;
		user = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where upper(o.login) = upper('" + this.login + "')");

			while (myRes.next()) {
				if (myRes.getString("login") == this.login) {
					return null;
				}
			}

			String sql = "insert into opday.officer (id, firstname, lastname, midlename, dateopen, dateclose, login, password, email) "
					+ "values (opday.sq_officer.nextval, ?, ?, ?, TO_CHAR(SYSDATE, 'dd.mm.yyyy hh24:mi:ss'), '01.01.1900 00:00:00', ?, ?, ?)";

			PreparedStatement ps = myConn.prepareStatement(sql); // компиляция sql
			// ps.setString(1, "1"); // тригер написать
			ps.setString(1, this.firstName);
			ps.setString(2, this.lastName);
			ps.setString(3, this.midleName);
			// ps.setString(5, SYSDATE());
			// ps.setString(6, "01.01.1900 00:00:00");
			ps.setString(4, this.login);
			ps.setString(5, this.password);
			ps.setString(6, this.eMail);
			ps.executeUpdate(); // выполнение запроса

			myRes = myStmt.executeQuery("select id from opday.officer o where upper(o.login) = upper('" + this.login
					+ "')" + "and o.password =" + this.password);

			while (myRes.next()) {
				iid = Integer.parseInt(myRes.getString("id"));
			}

			String sqlrole = "insert into opday.rights (id, id_user, id_taskgroup) values (opday.sq_rights.nextval, ?, ?)";
			PreparedStatement psrole = myConn.prepareStatement(sqlrole);
			psrole.setLong(1, iid);
			psrole.setLong(2, 2); // роль пользователь
			psrole.executeUpdate(); // выполнение запроса

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + iid);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
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
		return user;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User updatelastNameUser(String id, String firstname) throws DAOException {
		// обновление фамилии пользователя
		this.id = Integer.parseInt(id);
		this.firstName = firstname.toUpperCase();

		User user;
		user = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();

			String sql = "update opday.officer o set o.firstname = ? where o.id = ?";

			PreparedStatement ps = myConn.prepareStatement(sql); // компиляция sql
			ps.setString(1, this.firstName);
			ps.setLong(2, this.id); // тригер написать
			ps.executeUpdate(); // выполнение запроса

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + this.id);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
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
		return user;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String updateloginUser(int id) {
		// обновление логина узера
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ArrayList<User> adminsearchuserfirstnam(String firstname) throws DAOException {
		// поиск по фамилии и имени
		this.firstName = firstname;

		User user;
		user = null;
		
		ArrayList<User> arrayListUser = new ArrayList<User>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose, "
					+ " o.login, o.password, o.email, r.id_taskgroup"
					+ " from opday.officer o left outer join opday.rights r on o.id = r.id_user " 
					+ " where upper(o.firstname) like upper('%" + this.firstName + "%')"
					+ " order by 1");
			while (myRes.next()) {
				user = new User(); 
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));

				arrayListUser.add(user);
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
		return arrayListUser;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ArrayList<User> allUsers() throws DAOException {
		// возвращает всех пользователей
		User user = null;
		// user = null; // так не делать, т.к. не создастся объект
		ArrayList<User> arrayListUser = new ArrayList<User>();
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose, "
					+ " o.login, o.password, o.email, r.id_taskgroup"
					+ " from opday.officer o left outer join opday.rights r on o.id = r.id_user order by 1");
			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));

				arrayListUser.add(user);
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
		return arrayListUser;
	}

	@Override
	public User adminregistruser(String firstname, String lastname, String midleName, String login, String password,
			String eMail, String role) throws DAOException {
		// добавление пользователя от имени администратора

		this.firstName = firstname.toUpperCase();
		this.lastName = lastname.toUpperCase();
		this.midleName = midleName.toUpperCase();
		this.login = login.toUpperCase();
		this.password = password;
		this.eMail = eMail;
		this.role = Integer.parseInt(role);
		int iid;
		iid = 0;

		User user;
		user = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);

			myStmt = myConn.createStatement();

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where upper(o.login) = upper('" + this.login + "')");

			while (myRes.next()) {
				if (myRes.getString("login") == this.login) {
					return null;
				}
			}

			String sql = "insert into opday.officer (id, firstname, lastname, midlename, dateopen, dateclose, login, password, email) "
					+ "values (opday.sq_officer.nextval, ?, ?, ?, TO_CHAR(SYSDATE, 'dd.mm.yyyy hh24:mi:ss'), '01.01.1900 00:00:00', ?, ?, ?)";

			PreparedStatement ps = myConn.prepareStatement(sql); // компиляция sql
			// ps.setString(1, "1"); // тригер написать
			ps.setString(1, this.firstName);
			ps.setString(2, this.lastName);
			ps.setString(3, this.midleName);
			// ps.setString(5, SYSDATE());
			// ps.setString(6, "01.01.1900 00:00:00");
			ps.setString(4, this.login);
			ps.setString(5, this.password);
			ps.setString(6, this.eMail);
			ps.executeUpdate(); // выполнение запроса

			myRes = myStmt.executeQuery("select id from opday.officer o where upper(o.login) = upper('" + this.login
					+ "')" + "and o.password =" + this.password);

			while (myRes.next()) {
				iid = Integer.parseInt(myRes.getString("id"));
			}

			String sqlrole = "insert into opday.rights (id, id_user, id_taskgroup) values (opday.sq_rights.nextval, ?, ?)";
			PreparedStatement psrole = myConn.prepareStatement(sqlrole);
			psrole.setLong(1, iid);
			psrole.setLong(2, this.role); // роль пользователь
			psrole.executeUpdate(); // выполнение запроса

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + iid);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
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
		return user;
	}

	@Override
	public User admindeleteuser(String id) throws DAOException {
		// удаление пользователя по ID

		this.id = Integer.parseInt(id);

		User user;
		user = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);

			myStmt = myConn.createStatement();

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + this.id);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
			}

			myRes = myStmt.executeQuery("select o.id from opday.officer o where o.id = " + this.id);

			while (myRes.next()) {
				if (Integer.parseInt(myRes.getString("id")) == this.id) {
					// myRes = myStmt.executeQuery("select 1 from opday.orders o where o.id_user = "
					// + this.id);

					myRes = myStmt.executeQuery("select o.id_user from opday.orders o where o.id_user = " + this.id
							+ " group by o.id_user");

					if (myRes.next()) {
						while (!(myRes.next())) {
							if (Integer.parseInt(myRes.getString("id_user")) == this.id) {
								return null;
							} else {
								myStmt.executeUpdate("delete from opday.officer o where o.id = " + this.id);
							}
						}
					} else {
						myStmt.executeUpdate("delete from opday.officer o where o.id = " + this.id);
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

		return user;
	}

	@Override
	public User searchUserId(String id) throws DAOException {
		// поиск пользователя по ид
		this.id = Integer.parseInt(id);
		
		User user;
		user = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + this.id);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
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

		return user;
	}

	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User adminupduserdata(String id, String firstname, String lastname, String midleName, String login,
			String eMail, String role) throws DAOException {
		// обновляем данные пользователя если пароль не обновлялся 
		
		this.id = Integer.parseInt(id);
		this.firstName = firstname.toUpperCase();
		this.lastName = lastname.toUpperCase();
		this.midleName = midleName.toUpperCase();
		this.login = login.toUpperCase();
//		this.password = password;
		this.eMail = eMail;
		this.role = Integer.parseInt(role);

		User user;
		user = null;
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			String sql = "update opday.officer o set o.firstname = ?, o.email = ? where o.id = ?";
			PreparedStatement ps = myConn.prepareStatement(sql);
			ps.setString(1, this.firstName);
			ps.setString(2, this.eMail);
			ps.setLong(3, this.id); 
			ps.executeUpdate();
			
//			проверка, нужно ли обновлять роль, если она не менялась
			String sqlrole = "update opday.rights r set r.id_taskgroup = ? where r.id_user = ?";
			
			PreparedStatement ps2 = myConn.prepareStatement(sqlrole);
			ps2.setLong(1, this.role);
			ps2.setLong(2, this.id); 
			ps2.executeUpdate();

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + this.id);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
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
		return user;
	}

	
	@Override
	public User adminupduserdata(String id, String firstname, String lastname, String midleName, String login,
			String password, String eMail, String role) throws DAOException {
		// обновляем данные пользователя если пароль обновился 
		this.id = Integer.parseInt(id);
		this.firstName = firstname.toUpperCase();
		this.lastName = lastname.toUpperCase();
		this.midleName = midleName.toUpperCase();
		this.login = login.toUpperCase();
		this.password = password;
		this.eMail = eMail;
		this.role = Integer.parseInt(role);

		User user;
		user = null;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			String sql = "update opday.officer o set o.firstname = ?, o.password = ?, o.email = ? where o.id = ?";
			PreparedStatement ps = myConn.prepareStatement(sql);
			ps.setString(1, this.firstName);
			ps.setString(2, this.password);
			ps.setString(3, this.eMail);
			ps.setLong(4, this.id); 
			ps.executeUpdate();
			
//			проверка, нужно ли обновлять роль, если она не менялась
			String sqlrole = "update opday.rights r set r.id_taskgroup = ? where r.id_user = ?";
			
			PreparedStatement ps2 = myConn.prepareStatement(sqlrole);
			ps2.setLong(1, this.role);
			ps2.setLong(2, this.id); 
			ps2.executeUpdate();

			myRes = myStmt.executeQuery("select o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose,"
					+ " o.login, o.password, o.email, r.id_taskgroup from opday.officer o left outer join opday.rights r on o.id = r.id_user "
					+ " where o.id = " + this.id);

			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));
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
		return user;
	}

	@Override
	public Integer countRowsTable() throws DAOException {
		// возвращает количество строк в таблице
		
		int nRows;
		nRows = 0;

		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select count(o.id) as iid from opday.officer o");

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
	public ArrayList<User> allUserSearchPageDAO(int nStartRows, int nFinishRows) throws DAOException {
		// возвращает список узеров постранично
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;

		User user;
		user = null;

		ArrayList<User> arrayListUser = new ArrayList<User>();
		
		try {
			myConn = DriverManager.getConnection(SQLConnect.URL, SQLConnect.LOGIN, SQLConnect.PASSWORD);
			myStmt = myConn.createStatement();
			myRes = myStmt.executeQuery("select * from (select rownum r, o.id, o.firstname, o.lastname, o.midlename, o.dateopen, o.dateclose, "
					+ " o.login, o.password, o.email, r.id_taskgroup"
					+ " from opday.officer o left outer join opday.rights r on o.id = r.id_user order by id)"
					+ " where r between " + this.nStartRows +	" and " + this.nFinishRows);		
			while (myRes.next()) {
				user = new User();
				user.setId(Integer.parseInt(myRes.getString("id")));
				user.setFirstName(myRes.getString("firstname"));
				user.setLastName(myRes.getString("lastname"));
				user.setMidleName(myRes.getString("midlename"));
				user.setDateopen(myRes.getString("dateopen"));
				user.setDateclose(myRes.getString("dateclose"));
				user.setLogin(myRes.getString("login"));
				user.seteMail(myRes.getString("email"));
				user.setRole(Integer.parseInt(myRes.getString("id_taskgroup")));

				arrayListUser.add(user);
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
		return arrayListUser;
		
	}

}
