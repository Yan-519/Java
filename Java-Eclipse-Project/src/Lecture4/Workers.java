package Lecture4;

public class Workers implements MaxThings{

	private int[] bonuses;
	private Project[] projects;
	
	
	
	 public Workers(int[] bonuses, Project[] projects) {
		super();
		this.bonuses = bonuses;
		this.projects = projects;
	}

	 public int getMax() {
	        if (bonuses == null || bonuses.length == 0)
	            return -1;

	        int max = bonuses[0];

	        for (int i = 1; i < bonuses.length; i++) {
	            if (bonuses[i] > max)
	                max = bonuses[i];
	        }

	        return max;
	    }

	    @Override
	    public String getMaxName() {
	        if (bonuses == null || bonuses.length == 0 || projects == null)
	            return null;

	        int maxIndex = 0;

	        for (int i = 1; i < bonuses.length; i++) {
	            if (bonuses[i] > bonuses[maxIndex])
	                maxIndex = i;
	        }

	        return projects[maxIndex].getName();
	    }
	
}
