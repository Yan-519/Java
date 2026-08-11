package HW1;

import java.util.Arrays;

public class RestAdmin extends Admin {
	private Restaurant[] restaurants;
	private int count;


//	public RestAdmin(String name, int code, String userName, int password, Restaurant[] restaurants, int count) {
//		super(name, code, userName, password);
//		this.restaurants = restaurants;
//		this.count = Math.max(count, 0);
//	}

	public RestAdmin(String name, int code, String userName, int password) {
		super(name, code, userName, password);
		this.restaurants = new Restaurant[0];
		this.count = 0;
	}

	// adds a restaurant to the RestAdmin (if doesnt exists already)
	public void addRestaurant(Restaurant restaurant) {
		for (int i = 0; i < count; i++) {
			if(restaurants[i].getCode() == restaurant.getCode()) {
				return;
			}
		}

		if(restaurants.length == count) {
			restaurants = Arrays.copyOf(restaurants, count + 1);
		}

		restaurants[count ++ ] = restaurant;
	}

	// returns the Restaurant by restCode (if cant -> null)
	public Restaurant tryGetRestaurant(int restCode) {
		for (int i = 0; i < count; i++) {
			if(restaurants[i].getCode() == restCode) {
				return restaurants[i];
			}
		}

		return null;
	}


	public Restaurant[] getRestaurants() {
		return restaurants;
	}

	public int getCount() {
		return count;
	}


	public void setRestaurants(Restaurant[] restaurants) {
		this.restaurants = restaurants;
	}


	public void setCount(int count) {
		if( 0<= count) {
			this.count = count;
		}
	}


}
