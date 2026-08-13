package HW3.DataObjects;

public class Admin {

	private String name;
	private String userName;
	private int password;
	
	
	public Admin(String name, String userName, int password) {
		super();
		this.name = name;
		this.userName = userName;
		this.password = password;
	}

	public String getName() {
		return name;
	}
	
	
	public String getUserName() {
		return userName;
	}
	
	public int getPassword() {
		return password;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(int password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [name=" + name + ", userName=" + userName + ", password=" + password + "]";
	}
	
	
}
