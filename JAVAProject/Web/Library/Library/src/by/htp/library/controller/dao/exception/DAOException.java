package by.htp.library.dao.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = -7424108388015816081L;

	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}

}
