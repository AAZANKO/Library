package by.htp.library.entity;

public class User {

	public User() {
	}

	public User(String lastName, String surName, String midleName, String login, String eMail) {
	}

	public User(int id, String firstname, String lastName, String midleName, String dateopen, String dateclose,
			String login, String eMail) {
	}

	private int id;
	private String firstName;
	private String lastName;
	private String midleName;
	private String dateopen;
	private String dateclose;
	private String login;
	// private String password; // не хранить в объекте
	private String eMail;
	private int role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMidleName() {
		return midleName;
	}

	public void setMidleName(String midleName) {
		this.midleName = midleName;
	}

	public String getDateopen() {
		return dateopen;
	}

	public void setDateopen(String dateopen) {
		this.dateopen = dateopen;
	}

	public String getDateclose() {
		return dateclose;
	}

	public void setDateclose(String dateclose) {
		this.dateclose = dateclose;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateclose == null) ? 0 : dateclose.hashCode());
		result = prime * result + ((dateopen == null) ? 0 : dateopen.hashCode());
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((midleName == null) ? 0 : midleName.hashCode());
		result = prime * result + role;
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
		User other = (User) obj;
		if (dateclose == null) {
			if (other.dateclose != null)
				return false;
		} else if (!dateclose.equals(other.dateclose))
			return false;
		if (dateopen == null) {
			if (other.dateopen != null)
				return false;
		} else if (!dateopen.equals(other.dateopen))
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (midleName == null) {
			if (other.midleName != null)
				return false;
		} else if (!midleName.equals(other.midleName))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
	
	
}
