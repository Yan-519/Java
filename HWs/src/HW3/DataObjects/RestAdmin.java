package HW3.DataObjects;

import java.util.ArrayList;
import java.util.Set;

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
	
	public void removeRestaurants(Set<Integer> codes) {
		restaurants = new ArrayList<Restaurant>(restaurants.stream().filter(r -> !codes.contains(r.getCode())).toList());
	}
	
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}
	
	// get all open restaurants
	public ArrayList<Restaurant> getOpenRestaurants(){
		return new ArrayList<Restaurant>(restaurants.stream().filter(r -> r.isOpen()).toList());
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
		return "RestAdmin [name=" + name + ", userName=" + userName + ", password=" + password + ", restaurants="
				+ restaurants + ", code=" + code + "]";
	}

	
	

}
