package HW3.DataObjects;

import java.util.ArrayList;

public class RestAdmin extends Coded {
	
	private String name;
	private String userName;
	private String password;

	private ArrayList<Restaurant> restaurants;

	public RestAdmin(int code, String name, String userName, String password) {
		super(code);
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.restaurants = new ArrayList<>();
	}

	// add new restaurant
	public void addRestaurant(Restaurant restaurant) {
		if(restaurant != null && !restaurants.contains(restaurant))
			this.restaurants.add(restaurant);
	}
	
	// is contains the restaurant code
	public boolean containsRestaurant(int code) {
		return Coded.isContains(restaurants, code);
	}
	
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}
	
	// get all open restaurants
	public ArrayList<Restaurant> getOpenRestaurants(){
		ArrayList<Restaurant> res = new ArrayList<>();
		for(Restaurant restaurant: restaurants)
			if(restaurant.isOpen())
				res.add(restaurant);
		return res;
	}


	public String getName() {
		return name;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRestaurants(ArrayList<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	@Override
	public String toString() {
		return this.getUserName() + ": " + this.getCode();
	}
	
	

}
