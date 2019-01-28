package by.htp.library.service;

import java.util.ArrayList;

import by.htp.library.entity.User;
import by.htp.library.service.exception.ServiceException;

public interface UserService {

	User logination(String login, String password) throws ServiceException;

	User registration(String firstname, String lastname, String midleName, String login, String password,
			String eMail) throws ServiceException;
	
	User adminregistruser(String firstname, String lastname, String midleName, String login, String password,
			String eMail, String role) throws ServiceException;
	
	User admindeluser(String id) throws ServiceException;
	
	ArrayList<User> allUsersSearch() throws ServiceException;
	
	User adminupduserfirstnam(String id, String firstname) throws ServiceException;
	
	ArrayList<User> adminsearchuserfirstnam(String firstname) throws ServiceException;
	
	User searchuserid(String id) throws ServiceException;
	
	User admineditusertable(String id, String firstname, String lastname, String midleName, String login, String password,
			String eMail, String role) throws ServiceException;
	
	
	Integer countRowsUserTable() throws ServiceException;
	
	ArrayList<User> allUserSearchPage(int nStartRows, int nFinishRows) throws ServiceException;
	
}
