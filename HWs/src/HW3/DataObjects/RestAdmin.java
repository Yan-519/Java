package HW3.DataObjects;

import java.util.ArrayList;
import java.util.Set;

import HW3.DataObjects.Helpers.Coded;
import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.Utils.MessageBox;

public class RestAdmin extends Coded<RestAdmin> {
	
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
	
	

	private RestAdmin(int code, String name, String userName, String password, ArrayList<Restaurant> restaurants) {
		super(code);
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.restaurants = restaurants;
	}


	public RestAdmin() {super(-1);}

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
		return new ArrayList<Restaurant>(restaurants.stream().filter(r -> r.getIsOpen()).toList());
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

	@Override
	public String convert() {
		ArrayList<Integer> restCodes = new ArrayList<>(restaurants.stream().map(r -> r.getCode()).toList());

		return joiner(
			code,
			name,
			userName,
			password,
			restCodes
		);
	}

	@Override
	public ConvertorHolder<RestAdmin> convert(String in) {
		if (in == null || in.trim().isEmpty()) {
			return null;
		}

		String[] parts = in.trim().split(" ");
		if (parts.length < 5) {
			throw new IllegalArgumentException("Invalid input format for RestAdmin: " + in);
		}

		int parsedCode = Integer.parseInt(parts[0]);
		String parsedName = parts[1].replace("_", " ");
		String parsedUserName = parts[2].replace("_", " ");
		String parsedPassword = parts[3].replace("_", " ");

		ArrayList<Integer> codes = new ArrayList<Integer>();
		
		if(!parts[4].equals("none"))
			for(String RestCode : parts[4].split(","))
				codes.add(Integer.valueOf(RestCode));
		RestAdmin restAdmin = new RestAdmin(parsedCode, parsedName, parsedUserName, parsedPassword);
		return new ConvertorHolder<>( restAdmin, codes);
	}

	
	

}
