package RoomOrganisation;

public class student {

	private String name;
	private String gender;
	private String nationality;
	private String[] preferences;
	
	public student(String n, String g, String nation, String fP, String sP, String tP) {
		this.name = n;
		this.gender = g;
		this.nationality = nation;
		String[] array = {fP, sP, tP};
		this.preferences = array;
	} 
	
	public String getName() {
		return this.name;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public String getNation() {
		return this.nationality;
	}
	
	public String[] getPref() {
		return this.preferences;
	}
}

