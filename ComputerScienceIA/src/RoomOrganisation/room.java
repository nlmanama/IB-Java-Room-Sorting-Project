package RoomOrganisation;
import java.util.Arrays;

public class room {
	private int spaceAvailiable;
	private int fitness;
	private student[] Students;
	
	
	protected boolean changed;
	protected int nationalities;
	protected int preference;
	
	public room (int s) {
		this.spaceAvailiable = s;
		Students = new student[s];
		changed = false;
	}
	
	public int getSpace() {
		return this.spaceAvailiable;
	}
	
	public int getFit() {
		return this.fitness;
	}
	
	public student[] getStudents() {
		return this.Students;
	}
	
	
	//Search b in the list of preferences from a
	public int findMatch(student a, student b) {
		int matchScore = 0;
		for (int i = 0; i < 3; i++) {
			if(b.getName().equals(a.getPref()[i])) {
				matchScore = 3-i;
			}
		}
		return matchScore; 
	}
	
	public void addStudent(String n, String g, String nation, String fP, String sP, String tP) {
		int pos = Students.length - spaceAvailiable;
		Students[pos] = new student(n, g, nation, fP, sP, tP);
		spaceAvailiable -=1;
	}
	
	public void runFitCheck() {
		this.preference = 0;
		this.nationalities = 1;
		this.fitness = 1;
		int count;
		for (int i = 0; i<Students.length; i++) {
			count = i+1;
			while (count < Students.length) {
				fitness += findMatch(Students[i], Students[count]);
				preference +=findMatch(Students[i], Students[count]);
				count = count +1;
			}
			
			count = i-1;
			while (count >=0) {
				fitness += findMatch(Students[i], Students[count]);
				preference +=findMatch(Students[i], Students[count]);
				count = count -1;
			}
		}
		
		String[] Nationalities = new String[Students.length];
		for (int i = 0; i<Students.length; i++) {
			Nationalities[i] = Students[i].getNation();
		}
		Arrays.sort(Nationalities);
		for (int i = 0; i<Nationalities.length-1;i++) {
			if(!Nationalities[i].equals(Nationalities[i+1])) {
				fitness +=1;
				nationalities+=1;
			}
		}
	}
	
	public void modStudent(int i, student s) {
		Students[i] = s;
	}
	
}
