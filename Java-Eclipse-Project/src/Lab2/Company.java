package Lab2;

import java.util.Arrays;

public class Company {
	private int companyId;
	private String companyName;
	private Driver[] drivers;
	
	
	public Company() {
	}
	
	public Company(int companyId, String companyName, Driver[] drivers) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.drivers = Arrays.copyOf(drivers, drivers.length);
	}
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Driver[] getDrivers() {
		return drivers;
	}
	public void setDrivers(Driver[] drivers) {
		this.drivers = drivers;
	}
	
	// returns the best driver (biggest monthly talary ter trip)
	public int bestDriver() {
		double max = drivers[0].getMonthlySalaryPerTrip();
		int id = drivers[0].getDriverId();
		
		for(int i = 1; i < drivers.length; i++) {
			if(max < drivers[i].getMonthlySalaryPerTrip()) {
				max = drivers[i].getMonthlySalaryPerTrip();
				id = drivers[i].getDriverId();
			}
		}
		
		return id;
	}
}
