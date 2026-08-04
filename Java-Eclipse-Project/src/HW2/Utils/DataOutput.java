package HW2.Utils;

import java.util.ArrayList;

import HW2.DataObjects.Coded;
import HW2.DataObjects.Customer;
import HW2.DataObjects.Order;
import HW2.DataObjects.RestAdmin;
import HW2.DataObjects.Restaurant;
import HW2.DataObjects.Rider;

public class DataOutput {
	// show Coded objects
	public static <T extends Coded> void showCoded(T coded) {
        if (coded instanceof RestAdmin restAdmin) {
            System.out.println(restAdmin.getUserName() + ": " + restAdmin.getCode());

        } else if (coded instanceof Restaurant restaurant) {
            System.out.println(restaurant.getName() + ": " + restaurant.getCode());
            
        } else if (coded instanceof Customer customer) {
            System.out.println(customer.getName() + " " + customer.getLastName() + ": " + customer.getCode());

        } else if (coded instanceof Order order) {
        	System.out.println("OrderCode=" + order.getCode() + ": client=" + order.getClientCode() + ", restaurant=" + order.getRestaurantCode()
        				+ ", riderId=" + order.getRiderId() + ", basePrice=" + order.getBasePrice() + ", finalPrice=" + order.getFinalPrice() + ", status="
        				+ order.getDeliveryStatus());
        }
	}
	
	// show Coded objects
	public static <T extends Coded> boolean showCoded(ArrayList<T> codeds) {
		if (codeds.isEmpty())
	        return false;

		System.out.println(codeds.getFirst().getClass().getSimpleName() + " info:");

	    for (Coded coded : codeds) 
	    	showCoded(coded);
	    return true;
	}
	
	// show all aveilable Riders
	public static void showAveilableRiders(ArrayList<Rider> riders) {
		for(Rider rider_: riders) {
			if(rider_.isAvailable()) {
				System.out.println("Riders Id:");
				for(Rider rider: riders)
					if(rider.isAvailable())
						System.out.println(rider.getName() + " " + rider.getLastName() + ": " + rider.getId());
				return;
			}
		}
	}
}
