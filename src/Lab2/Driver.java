package Lab2;

public class Driver {
	private int driverId;
	private String name;
	private double monthlySalary;
	private int monthlyTrips;
	
	
	public Driver() {
		this(0, 0, 0);
	}

	public Driver(int driverId, double monthlySalary, int monthlyTrips) {
		super();
		this.driverId = driverId;
		this.monthlySalary = Math.max(monthlySalary, 0);
		this.monthlyTrips = Math.max(monthlyTrips, 0);
	}

	public Driver(int driverId, String name, double monthlySalary, int monthlyTrips) {
		this.driverId = driverId;
		this.name = name;
		this.monthlySalary = Math.max(monthlySalary, 0);
		this.monthlyTrips = Math.max(monthlyTrips, 0);
	}
	
	
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMonthlySalary() {
		return monthlySalary;
	}
	public void setMonthlySalary(double monthlySalary) {
		if(0<= monthlySalary)
		this.monthlySalary = monthlySalary;
	}
	public int getMonthlyTrips() {
		return monthlyTrips;
	}
	public void setMonthlyTrips(int monthlyTrips) {
		if(0<= monthlyTrips)
		this.monthlyTrips = monthlyTrips;
	}

	/// Function explonation:
	// Good morrow, gentle traveler of this humble code. Attend with patient eye,
	// for herein is revealed the purpose of this most worthy computation. This
	// function, by careful measure and honest arithmetic, seeketh to discover the
	// monthly salary earned for every single journey completed. It gathereth the
	// fruits of labor spread throughout the passing month and divideth them with
	// fairness amongst the number of trips undertaken, so that each voyage may
	// bear witness to its proper reward. Should many roads have been traveled,
	// each shall claim but its rightful portion; should few have been taken, the
	// value of each shall rise accordingly. Thus is balance preserved, and neither
	// excess nor want shall cloud the reckoning. Let no careless soul alter this
	// calculation without due thought, lest confusion reign where order once did
	// flourish. For numbers, though silent, possess a truth that mocketh deceit,
	// and an error of but one figure may cast honest wages into shadow. If ever
	// the manner of payment be changed by decree of merchant, master, or crown,
	// let this place be visited first, that the reckoning remain just and faithful.
	// Handle all values with prudence, guard against division by naught, and keep
	// the intent as clear as the noonday sun. In so doing, this function shall
	// continue to serve its noble office, rendering unto every trip its deserved
	// share of the month's reward, neither more nor less, until time itself grows
	// weary of counting.
	
	//// in other words: returns the Monthly Salary Per Trip
	public double getMonthlySalaryPerTrip() {
		if(monthlyTrips == 0)
			return 0;
		
		return monthlySalary / monthlyTrips;
	}
	
	// returns if the Monthly Salary Per Trip is more than 150
	public boolean isEfficient() {
		return 150 < getMonthlySalaryPerTrip();
	}
	

	@Override
	public String toString() {
		return "Driver [driverId=" + driverId + ", name=" + name + "]";
	}
	
	
}
