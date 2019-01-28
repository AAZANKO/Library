package by.htp.library.entity;

public class Zakaz {

	public Zakaz() {
	}

	public Zakaz(int zakazId, String userFirstName, String userLastName, String bookName, String bookAutor,
			String zakazDate, String zakazStatus) {
	}

	private int zakazId;
	private String userFirstName;
	private String userLastName;
	private String bookName;
	private String bookAutor;
	private String zakazDate;
	private String zakazStatus;
	private String zakazSumm;

	public int getZakazId() {
		return zakazId;
	}

	public void setZakazId(int zakazId) {
		this.zakazId = zakazId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAutor() {
		return bookAutor;
	}

	public void setBookAutor(String bookAutor) {
		this.bookAutor = bookAutor;
	}

	public String getZakazDate() {
		return zakazDate;
	}

	public void setZakazDate(String zakazDate) {
		this.zakazDate = zakazDate;
	}

	public String getZakazStatus() {
		return zakazStatus;
	}

	public void setZakazStatus(String zakazStatus) {
		this.zakazStatus = zakazStatus;
	}

	public String getZakazSumm() {
		return zakazSumm;
	}

	public void setZakazSumm(String zakazSumm) {
		this.zakazSumm = zakazSumm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookAutor == null) ? 0 : bookAutor.hashCode());
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + ((userFirstName == null) ? 0 : userFirstName.hashCode());
		result = prime * result + ((userLastName == null) ? 0 : userLastName.hashCode());
		result = prime * result + ((zakazDate == null) ? 0 : zakazDate.hashCode());
		result = prime * result + zakazId;
		result = prime * result + ((zakazStatus == null) ? 0 : zakazStatus.hashCode());
		result = prime * result + ((zakazSumm == null) ? 0 : zakazSumm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zakaz other = (Zakaz) obj;
		if (bookAutor == null) {
			if (other.bookAutor != null)
				return false;
		} else if (!bookAutor.equals(other.bookAutor))
			return false;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (userFirstName == null) {
			if (other.userFirstName != null)
				return false;
		} else if (!userFirstName.equals(other.userFirstName))
			return false;
		if (userLastName == null) {
			if (other.userLastName != null)
				return false;
		} else if (!userLastName.equals(other.userLastName))
			return false;
		if (zakazDate == null) {
			if (other.zakazDate != null)
				return false;
		} else if (!zakazDate.equals(other.zakazDate))
			return false;
		if (zakazId != other.zakazId)
			return false;
		if (zakazStatus == null) {
			if (other.zakazStatus != null)
				return false;
		} else if (!zakazStatus.equals(other.zakazStatus))
			return false;
		if (zakazSumm == null) {
			if (other.zakazSumm != null)
				return false;
		} else if (!zakazSumm.equals(other.zakazSumm))
			return false;
		return true;
	}
	
	
}
