package HW2;

import java.util.ArrayList;

public class RestAdmin extends Coded {
	
	private String name;
	private String userName;
	private int password;

	private ArrayList<Restaurant> restaurants;

	public RestAdmin(int code, String name, String userName, int password) {
		super(code);
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.restaurants = new ArrayList<>();
	}
	
	
	
	public RestAdmin(int code, String name, String userName, int password, ArrayList<Restaurant> restaurants) {
		this(code, name, userName, password);
		this.restaurants = restaurants;
	}



	public void addRestaurant(Restaurant restaurant) {
		if(restaurant != null && !restaurants.contains(restaurant))
			this.restaurants.add(restaurant);
	}
	
	public Restaurant tryGetRestaurant(int code) {
		return Coded.tryGetCoded(restaurants, code);
	}
	
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
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

}
