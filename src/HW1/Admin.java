package HW1;

public class Admin {

	protected String name;
	protected int code;
	protected String userName;
	protected int password;
	
	
	public Admin(String name, int code, String userName, int password) {
		super();
		this.name = name;
		this.code = Math.max(code, 0);
		this.userName = userName;
		this.password = password;
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getCode() {
		return code;
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


	public void setCode(int code) {
		if(0<= code)
		this.code = code;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(int password) {
		this.password = password;
	}
	
	
}
