package by.htp.library.dao;

import java.util.ArrayList;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.User;

public interface UserDAO {

	User logination(String login, String password) throws DAOException;

	User registration(String firstname, String lastname, String midleName, String login, String password, String eMail)
			throws DAOException;

	User adminregistruser(String firstname, String lastname, String midleName, String login, String password,
			String eMail, String role) throws DAOException;

	User admindeleteuser(String id) throws DAOException;

	User updatelastNameUser(String id, String firstname) throws DAOException;

	String updateloginUser(int id) throws DAOException;

	ArrayList<User> adminsearchuserfirstnam(String firstName) throws DAOException;

	ArrayList<User> allUsers() throws DAOException;
	
	User searchUserId (String id) throws DAOException;
	
	User adminupduserdata(String id, String firstname, String lastname, String midleName, String login,
			String eMail, String role) throws DAOException;
	
	User adminupduserdata(String id, String firstname, String lastname, String midleName, String login, String password,
			String eMail, String role) throws DAOException;	
	
	Integer countRowsTable() throws DAOException;
	
	ArrayList<User> allUserSearchPageDAO(int nStartRows, int nFinishRows) throws DAOException;
}
