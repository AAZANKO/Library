package by.htp.library.service.impl;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.User;
import by.htp.library.service.UserService;
import by.htp.library.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
	
	public UserServiceImpl() {}

	private String id;
	private String firstName;
	private String lastName;
	private String midleName;
	private String login;
	private String password;
	private String eMail;
	private String role;

	private Matcher mId;
	private Matcher mFirstName;
	private Matcher mLastName;
	private Matcher mMidleName;
	private Matcher mLogin;
	private Matcher mEMail;
	private Matcher mRole;

	private boolean bId;
	private boolean bFirstName;
	private boolean bLastName;
	private boolean bMidleName;
	private boolean bLogin;
	private boolean bEMail;
	private boolean bRole;
	
	private int nStartRows;
	private int nFinishRows;

	private Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]");

	////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User logination(String login, String password) throws ServiceException {
		// Validation immer da

		this.login = login.trim();
		this.password = password.trim();

		if (login == null || login.isEmpty()) {
			return null;
		}
		if (password == null || password.isEmpty()) {
			return null;
		}

		// Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]");
		mLogin = p.matcher(this.login);
		bLogin = mLogin.find();
		
		if (bLogin) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.logination(this.login, this.password);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;
	}

	////////////////////////////////////////////////////////////////////////////////////
	public User logination(int id, String login, String password) throws ServiceException {
		// Validation immer da

		this.login = login.trim();
		this.password = password.trim();

		if (login == null || login.isEmpty()) {
			return null;
		}
		if (password == null || password.isEmpty()) {
			return null;
		}

		// Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]");
		mLogin = p.matcher(this.login);
		bLogin = mLogin.find();

		if (bLogin) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.logination(this.login, this.password);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User registration(String firstName, String lastName, String midleName, String login, String password,
			String eMail) throws ServiceException {
		// Validation
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		this.midleName = midleName.trim();
		this.login = login.trim();
		this.password = password.trim(); // затереть пароль
		this.eMail = eMail.trim();

		if (this.firstName == null || this.firstName.isEmpty()) {
			return null;
		}
		if (this.lastName == null || this.lastName.isEmpty()) {
			return null;
		}
		if (this.midleName == null || this.midleName.isEmpty()) {
			return null;
		}
		if (this.login == null || this.login.isEmpty()) {
			return null;
		}
		if (this.password == null || this.password.isEmpty()) {
			return null;
		}
		if (this.eMail == null || this.eMail.isEmpty()) {
			return null;
		}

		// Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]"); // вынес к полям класса
		Pattern pEMail = Pattern.compile("([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)");
		// Pattern pPhone = Pattern.compile("([\\d{2}\\-\\d{2}])");

		mFirstName = p.matcher(this.firstName);
		mLastName = p.matcher(this.lastName);
		mMidleName = p.matcher(this.midleName);
		mLogin = p.matcher(this.login);
		mEMail = pEMail.matcher(this.eMail);
		// mPhone = pPhone.matcher(this.ePhonel);

		bFirstName = mFirstName.find();
		bLastName = mLastName.find();
		bMidleName = mMidleName.find();
		bLogin = mLogin.find();
		bEMail = mEMail.matches();
		// bPhone = mPhone.matches();

		if (bFirstName) {
			return null;
		} else if (bLastName) {
			return null;
		} else if (bMidleName) {
			return null;
		} else if (bLogin) {
			return null;
		} else if (!(bEMail)) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.registration(this.firstName, this.lastName, this.midleName, this.login, this.password,
					this.eMail);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return user;
	}

	@Override
	public ArrayList<User> allUsersSearch() throws ServiceException {
		// все пользователи

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		ArrayList<User> arrUser;

		try {
			arrUser = userDAO.allUsers();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrUser;

	}

	@Override
	public User adminregistruser(String firstname, String lastname, String midleName, String login, String password,
			String eMail, String role) throws ServiceException {
		// Validation

		this.firstName = firstname.trim();
		this.lastName = lastname.trim();
		this.midleName = midleName.trim();
		this.login = login.trim();
		this.password = password.trim(); // затереть пароль
		this.eMail = eMail.trim();
		this.role = role.trim();

		if (this.firstName == null || this.firstName.isEmpty()) {
			return null;
		}
		if (this.lastName == null || this.lastName.isEmpty()) {
			return null;
		}
		if (this.midleName == null || this.midleName.isEmpty()) {
			return null;
		}
		if (this.login == null || this.login.isEmpty()) {
			return null;
		}
		if (this.password == null || this.password.isEmpty()) {
			return null;
		}
		if (this.eMail == null || this.eMail.isEmpty()) {
			return null;
		}
		if ((this.role == null || this.role.isEmpty())
				&& (Integer.parseInt(this.role) < 1 || Integer.parseInt(this.role) > 5)) {
			return null;
		}

		// Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]"); // вынес к полям класса
		Pattern pEMail = Pattern.compile("([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)");
		// Pattern pPhone = Pattern.compile("([\\d{2}\\-\\d{2}])");

		mFirstName = p.matcher(this.firstName);
		mLastName = p.matcher(this.lastName);
		mMidleName = p.matcher(this.midleName);
		mLogin = p.matcher(this.login);
		mEMail = pEMail.matcher(this.eMail);
		// mPhone = pPhone.matcher(this.ePhonel);
		mRole = p.matcher(this.role);

		bFirstName = mFirstName.find();
		bLastName = mLastName.find();
		bMidleName = mMidleName.find();
		bLogin = mLogin.find();
		bEMail = mEMail.matches();
		// bPhone = mPhone.matches();
		bRole = mRole.find();

		if (bFirstName) {
			return null;
		} else if (bLastName) {
			return null;
		} else if (bMidleName) {
			return null;
		} else if (bLogin) {
			return null;
		} else if (!(bEMail)) {
			return null;
		} else if (bRole) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.adminregistruser(this.firstName, this.lastName, this.midleName, this.login, this.password,
					this.eMail, this.role);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;

	}

	/////////////////////////////////////////
	@Override
	public User admindeluser(String id) throws ServiceException {
		// удаление пользователя по ID

		this.id = id.trim();

		if (this.id == null || this.id.isEmpty()) {
			return null;
		}
		mId = p.matcher(this.id);

		bId = mId.find();

		if (bId) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.admindeleteuser(this.id);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;
	}

	@Override
	public User adminupduserfirstnam(String id, String firstname) throws ServiceException {
		// Обновление фамилии пользователя

		this.id = id.trim();
		this.firstName = firstname.trim();

		if (this.id == null || this.id.isEmpty()) {
			return null;
		}
		if (this.firstName == null || this.firstName.isEmpty()) {
			return null;
		}

		mId = p.matcher(this.id);
		mFirstName = p.matcher(this.firstName);

		bId = mId.find();
		bFirstName = mFirstName.find();

		if (bId) {
			return null;
		}
		if (bFirstName) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.updatelastNameUser(this.id, this.firstName);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;
	}

	@Override
	public ArrayList<User> adminsearchuserfirstnam(String firstname) throws ServiceException {
		// поиск пользователя по фамилии

		this.firstName = firstname.trim();

		if (this.firstName == null || this.firstName.isEmpty()) {
			return null;
		}

		mFirstName = p.matcher(this.firstName);
		bFirstName = mFirstName.find();
		if (bFirstName) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		ArrayList<User> arrUser;

		try {
			arrUser = userDAO.adminsearchuserfirstnam(this.firstName);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrUser;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User searchuserid(String id) throws ServiceException {
		// поиск пользователя по ид
		this.id = id.trim();

		if ((this.id == null || this.id.isEmpty()) && (Integer.parseInt(this.id) < 1)) {
			return null;
		}

		mId = p.matcher(this.id);
		bId = mId.find();

		if (bId) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			user = userDAO.searchUserId(this.id);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public User admineditusertable(String id, String firstname, String lastname, String midleName, String login,
			String password, String eMail, String role) throws ServiceException {
		// сохранение изменений отредактированного пользователя в таблице

		this.id = id.trim();
		this.firstName = firstname.trim();
		this.lastName = lastname.trim();
		this.midleName = midleName.trim();
		this.login = login.trim();
		this.password = password.trim(); // затереть пароль
		this.eMail = eMail.trim();
		this.role = role.trim();

		if ((this.id == null || this.id.isEmpty()) && (Integer.parseInt(this.id) < 1)) {
			return null;
		}
		if (this.firstName == null || this.firstName.isEmpty()) {
			return null;
		}
		if (this.lastName == null || this.lastName.isEmpty()) {
			return null;
		}
		if (this.midleName == null || this.midleName.isEmpty()) {
			return null;
		}
		if (this.login == null || this.login.isEmpty()) {
			return null;
		}
		// if (this.password == null || this.password.isEmpty()) {
		// return null;
		// }
		if (this.eMail == null || this.eMail.isEmpty()) {
			return null;
		}
		if ((this.role == null || this.role.isEmpty())
				&& (Integer.parseInt(this.role) < 1 || Integer.parseInt(this.role) > 5)) {
			return null;
		}

		// Pattern p = Pattern.compile("[\\@&$#?!*%\'\"]"); // вынес к полям класса
		Pattern pEMail = Pattern.compile("([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)");
		// Pattern pPhone = Pattern.compile("([\\d{2}\\-\\d{2}])");

		mId = p.matcher(this.id);
		mFirstName = p.matcher(this.firstName);
		mLastName = p.matcher(this.lastName);
		mMidleName = p.matcher(this.midleName);
		mLogin = p.matcher(this.login);
		mEMail = pEMail.matcher(this.eMail);
		// mPhone = pPhone.matcher(this.ePhonel);
		mRole = p.matcher(this.role);

		bId = mId.find();
		bFirstName = mFirstName.find();
		bLastName = mLastName.find();
		bMidleName = mMidleName.find();
		bLogin = mLogin.find();
		bEMail = mEMail.matches();
		// bPhone = mPhone.matches();
		bRole = mRole.find();

		if (bId) {
			return null;
		} else if (bFirstName) {
			return null;
		} else if (bLastName) {
			return null;
		} else if (bMidleName) {
			return null;
		} else if (bLogin) {
			return null;
		} else if (!(bEMail)) {
			return null;
		} else if (bRole) {
			return null;
		}

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		User user;

		try {
			if (this.password == null || this.password.isEmpty()) {
				user = userDAO.adminupduserdata(this.id, this.firstName, this.lastName, this.midleName, this.login,
						this.eMail, this.role);
			} else {
				user = userDAO.adminupduserdata(this.id, this.firstName, this.lastName, this.midleName, this.login,
						this.password, this.eMail, this.role);
			}
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}

		return user;
	}

	@Override
	public Integer countRowsUserTable() throws ServiceException {
		// количество строк в таблиц узер
		
		int countRowsTableUser;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			countRowsTableUser = userDAO.countRowsTable();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return countRowsTableUser;
	}

	@Override
	public ArrayList<User> allUserSearchPage(int nStartRows, int nFinishRows) throws ServiceException {
		// возвращает список пользователей с нач строки до конечной
		
		this.nStartRows = nStartRows;
		this.nFinishRows = nFinishRows;

		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		ArrayList<User> arrUser = new ArrayList<User>();

		try {
			arrUser = userDAO.allUserSearchPageDAO(this.nStartRows, this.nFinishRows);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		return arrUser;
	}

}
