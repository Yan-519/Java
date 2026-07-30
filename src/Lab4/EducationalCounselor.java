package Lab4;

public class EducationalCounselor extends Employee {
	public static final String 
			GeneralCounsel = "GeneralCounsel", 
			EducationalConsultant = "EducationalConsultant", 
			EducationTeamAdvisor = "EducationTeamAdvisor";
	
    private String specialization;

    public EducationalCounselor(String name, String specialization) {
        super(name);
        this.specialization = GeneralCounsel;
        setSpecialization(specialization);
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
    	if(specialization.equalsIgnoreCase(GeneralCounsel) || 
    			specialization.equalsIgnoreCase(EducationTeamAdvisor) ||
    			specialization.equalsIgnoreCase(EducationalConsultant))
    		this.specialization = specialization;
    }
    
    @Override
    public boolean getByType() {
    	return specialization.equals(EducationalConsultant);
    }

    @Override
    public String toString() {
        return super.toString() + " | Counselor [Specialization=" + specialization + "]";
    }
}