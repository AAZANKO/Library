package by.htp.library.service.exception;

public class ServiceException extends Exception{
	
	private static final long serialVersionUID = -4250666880196399132L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

}
