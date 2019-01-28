package by.htp.library.entity;

public class Book {

	public Book() {
	}

	public Book(int id, String nameBook, String autorBook, String dateBook, String priceBook, String saleBook,
			String ganreBook, String lesenBook) {
	}

	private int id;
	private String nameBook;
	private String autorBook;
	private String dateBook;
	private String priceBook;
	private String saleBook;
	private String ganreBook;
	private String lesenBook;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameBook() {
		return nameBook;
	}

	public void setNameBook(String nameBook) {
		this.nameBook = nameBook;
	}

	public String getAutorBook() {
		return autorBook;
	}

	public void setAutorBook(String autorBook) {
		this.autorBook = autorBook;
	}

	public String getDateBook() {
		return dateBook;
	}

	public void setDateBook(String dateBook) {
		this.dateBook = dateBook;
	}

	public String getPriceBook() {
		return priceBook;
	}

	public void setPriceBook(String priceBook) {
		this.priceBook = priceBook;
	}

	public String getSaleBook() {
		return saleBook;
	}

	public void setSaleBook(String saleBook) {
		this.saleBook = saleBook;
	}

	public String getGanreBook() {
		return ganreBook;
	}

	public void setGanreBook(String ganreBook) {
		this.ganreBook = ganreBook;
	}

	public String getLesenBook() {
		return lesenBook;
	}

	public void setLesenBook(String lesenBook) {
		this.lesenBook = lesenBook;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autorBook == null) ? 0 : autorBook.hashCode());
		result = prime * result + ((dateBook == null) ? 0 : dateBook.hashCode());
		result = prime * result + ((ganreBook == null) ? 0 : ganreBook.hashCode());
		result = prime * result + id;
		result = prime * result + ((lesenBook == null) ? 0 : lesenBook.hashCode());
		result = prime * result + ((nameBook == null) ? 0 : nameBook.hashCode());
		result = prime * result + ((priceBook == null) ? 0 : priceBook.hashCode());
		result = prime * result + ((saleBook == null) ? 0 : saleBook.hashCode());
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
		Book other = (Book) obj;
		if (autorBook == null) {
			if (other.autorBook != null)
				return false;
		} else if (!autorBook.equals(other.autorBook))
			return false;
		if (dateBook == null) {
			if (other.dateBook != null)
				return false;
		} else if (!dateBook.equals(other.dateBook))
			return false;
		if (ganreBook == null) {
			if (other.ganreBook != null)
				return false;
		} else if (!ganreBook.equals(other.ganreBook))
			return false;
		if (id != other.id)
			return false;
		if (lesenBook == null) {
			if (other.lesenBook != null)
				return false;
		} else if (!lesenBook.equals(other.lesenBook))
			return false;
		if (nameBook == null) {
			if (other.nameBook != null)
				return false;
		} else if (!nameBook.equals(other.nameBook))
			return false;
		if (priceBook == null) {
			if (other.priceBook != null)
				return false;
		} else if (!priceBook.equals(other.priceBook))
			return false;
		if (saleBook == null) {
			if (other.saleBook != null)
				return false;
		} else if (!saleBook.equals(other.saleBook))
			return false;
		return true;
	}

	
	
	
}
