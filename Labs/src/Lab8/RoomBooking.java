package Lab8;

import java.util.Comparator;

public class RoomBooking {
	private static int idCounter = 1;
	
	public enum RoomTypes {Standard, Delux, Suite}

	private int id;
	private String fullName;
	private int nights;
	private RoomTypes roomType;

	
	public RoomBooking(String fullName, int nights, RoomTypes roomType) {
		super();
		this.id = idCounter++;
		this.fullName = fullName;
		this.nights = nights;
		this.roomType = roomType;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getNights() {
		return nights;
	}
	public void setNights(int nights) {
		this.nights = nights;
	}
	public RoomTypes getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomTypes roomType) {
		this.roomType = roomType;
	}


	@Override
	public String toString() {
		return "RoomBooking [id=" + id + ", fullName=" + fullName + ", nights=" + nights + ", roomType=" + roomType + "]";
	}
	
	public static Comparator<RoomBooking> getNightsComparatorDescending() {
        return (b1, b2) -> Integer.compare(b2.getNights(), b1.getNights());
    }
}
