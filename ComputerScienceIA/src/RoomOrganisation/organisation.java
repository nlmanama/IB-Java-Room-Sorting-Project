package RoomOrganisation;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;

public class organisation {

	private ArrayList<String[]> data = new ArrayList<String[]>();
	private int[] boySpaces;
	private int[] girlSpaces;
	private int Generation;
	private int totalBRooms;
	private int totalGRooms;
	private room[] boyRooms;
	private room[] girlRooms;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		organisation org = new organisation();
	}
	
	public organisation() {
		mainFrame window = new mainFrame();
		window.frame.setVisible(true);
		Generation = 1;
		
		//Need to make sure it's filled first before requesting for it
		while (!window.hasBoy || !window.hasFile || !window.hasGirl) {
			boySpaces = window.getBSpace();
			girlSpaces = window.getGSpace();
			data = window.getData();
		}
		
		totalBRooms = sumArray(boySpaces);
		totalGRooms = sumArray(girlSpaces);
		
		boyRooms = new room[totalBRooms];
		boyRooms = createRoom(boySpaces, boyRooms);
		girlRooms = new room[totalGRooms];
		girlRooms = createRoom(girlSpaces, girlRooms);

		//Randomly assign rooms whilst creating the rooms for the first generation
		randomAssignRooms();
		findFitness();
		//loop whilst under a certain amount of generations or have not met aim fitness
		while(Generation < 50 && totalFitness <60) {
			crossover();
			findFitness();
			mutate();
			findFitness();
			Generation +=1;
		}
		writeFile(boyRooms, girlRooms);
	}
	
	//Simple method to sum all elements of array
	private int sumArray(int[] a) {
		int sum = 0;
		for (int i = 0; i<a.length; i++) {
			sum+= a[i];
		}
		return sum;
	}
	
	//Creating rooms with correct number of spaces as per input
	private room[] createRoom(int[] spaces, room[] Room) {
		int position = 0;
		for (int i = 0; i <3; i++) {
			for (int x = position; x < position+spaces[i]; x++) {
				Room[x] = new room(i+2);
			}
			position = position + spaces[i];
		}
		return Room;
	}

	//Method to randomly assign rooms
	private void randomAssignRooms() {
		Random rand = new Random();
		//3 different types of rooms as per pre-defined
		for (int i = 0; i < data.size(); i++) {
			//Boys and girls room separate
			if(data.get(i)[1].equals("M")) {
				//Generate random index for a room, make sure it has space
				int roomPos = rand.nextInt(totalBRooms);
				while (boyRooms[roomPos].getSpace() == 0) {
					roomPos = rand.nextInt(totalBRooms);
				}
				//call method add student to create a student object into that room
				boyRooms[roomPos].addStudent(data.get(i)[0], data.get(i)[1], data.get(i)[2], data.get(i)[3], data.get(i)[4], data.get(i)[5]);		
			}
			//repeat for girls
			else {
				int roomPos = rand.nextInt(totalGRooms);
				while (girlRooms[roomPos].getSpace() == 0) {
					roomPos = rand.nextInt(totalGRooms);
				}
				girlRooms[roomPos].addStudent(data.get(i)[0], data.get(i)[1], data.get(i)[2], data.get(i)[3], data.get(i)[4], data.get(i)[5]);
			}
		}	
	}
	
	private int totalFitness;
	private int bKeep;
	private int gKeep;
	private int[] bElite;
	private int[] gElite;
	private int[] bFitness;
	private int[] gFitness;
	
	
	private void findFitness() {
		bFitness = new int[totalBRooms];
		gFitness = new int[totalGRooms];
		
		int bTotalFit = 0;
		int gTotalFit = 0;
		//Loop to total amount of rooms, run fit check for all
		for (int i = 0; i < totalBRooms; i++) {
			boyRooms[i].runFitCheck();
			bFitness[i] = boyRooms[i].getFit();
			bTotalFit += boyRooms[i].getFit();
		}
		for (int i = 0; i < totalGRooms; i++) {
			girlRooms[i].runFitCheck();
			gFitness[i] = girlRooms[i].getFit();
			gTotalFit += girlRooms[i].getFit();
		}
		
		totalFitness = bTotalFit + gTotalFit;
		
		if (totalBRooms > 5) {
			if(totalBRooms%2==1) {
				bKeep = 3;
			}
			else {
				bKeep = 2;
			}
		}
		else {
			bKeep = 0;
		}
		int[] bFindFitness = bFitness;
		Arrays.sort(bFindFitness);
		bElite = new int[bKeep];
		for(int i = 0; i<bKeep;i++) {
			for(int x = 0; x<bFitness.length;x++) {
				if (bFindFitness[bFindFitness.length-i-1] == bFitness[x]) {
					bElite[i] = x;
				}
			}
		}
		
		if (totalGRooms > 5) {
			if(totalGRooms%2==1) {
				gKeep = 3;
			}
			else {
				gKeep = 2;
			}
		}
		else {
			gKeep = 0;
		}
	
		gElite = new int[gKeep];
		int[] gFindFitness = gFitness;
		Arrays.sort(gFindFitness);
		gElite = new int[gKeep];
		for(int i = 0; i<gKeep;i++) {
			for(int x = 0; x<gFitness.length;x++) {
				if (gFindFitness[gFindFitness.length-i-1] == gFitness[x]) {
					gElite[i] = x;
				}
			}
		}
		
	}
	

	 
	private void crossover() {
		Random ran = new Random();
		int swap = totalBRooms - bKeep;
		//Generate random index for 2 rooms, make sure they haven't been crossed, make sure they are not repeats
		for (int i = 0; i < swap/2; i++) {
			int roomIndex1 = ran.nextInt(3);
			while(boyRooms[roomIndex1].changed == true) {
				roomIndex1 = ran.nextInt(3);
			}
			int roomIndex2 = ran.nextInt(3);
			while(boyRooms[roomIndex2].changed == true && roomIndex2==roomIndex1) {
				roomIndex2 = ran.nextInt(3);
			}
			room room1 = boyRooms[roomIndex1];
			room room2 = boyRooms[roomIndex2];
			
			//Get a random student from each room
			int stuIndex1 = ran.nextInt(room1.getStudents().length);
			int stuIndex2 = ran.nextInt(room2.getStudents().length);
			//Swap them with a temporary variable
			student tempStudent = room1.getStudents()[stuIndex1];
			boyRooms[roomIndex1].modStudent(stuIndex1, room2.getStudents()[stuIndex2]);
			boyRooms[roomIndex2].modStudent(stuIndex2, tempStudent);
			boyRooms[roomIndex1].changed = true;
			boyRooms[roomIndex2].changed = true;
		} 
		//repeat for girls
		
		swap = totalGRooms - gKeep;
		for(int i = 0; i <swap/2; i++) {
			int roomIndex1 = ran.nextInt(3);
			while(girlRooms[roomIndex1].changed == true) {
				roomIndex1 = ran.nextInt(3);
			}
			int roomIndex2 = ran.nextInt(3);
			while(girlRooms[roomIndex2].changed == true && roomIndex2==roomIndex1) {
				roomIndex2 = ran.nextInt(3);
			}
			
			room room1 = girlRooms[roomIndex1];
			room room2 = girlRooms[roomIndex2];
			
			int stuIndex1 = ran.nextInt(room1.getStudents().length);
			int stuIndex2 = ran.nextInt(room2.getStudents().length);
			student tempStudent = room1.getStudents()[stuIndex1];
			girlRooms[roomIndex1].modStudent(stuIndex1, room2.getStudents()[stuIndex2]);
			girlRooms[roomIndex2].modStudent(stuIndex2, tempStudent);
			girlRooms[roomIndex1].changed = true;
			girlRooms[roomIndex2].changed = true;
		}
		
		//reset them all to not changed for next round
		for(int i = 0; i<3;i++) {
			girlRooms[i].changed = false;
			boyRooms[i].changed = false;
		}
	}
	
	public void mutate() {
		//Random time to mutate
		Random ran = new Random();
		int timesMutate = ran.nextInt(3);
		//Swap again like crossover
		for (int i = 0; i <timesMutate; i++) {
			int roomIndex1 = ran.nextInt(3);
			int roomIndex2 = ran.nextInt(3);
			while(roomIndex2==roomIndex1) {
				roomIndex2 = ran.nextInt(3);
			}
			room room1 = boyRooms[roomIndex1];
			room room2 = boyRooms[roomIndex2];
			
			int stuIndex1 = ran.nextInt(room1.getStudents().length);
			int stuIndex2 = ran.nextInt(room2.getStudents().length);
			student tempStudent = room1.getStudents()[stuIndex1];
			boyRooms[roomIndex1].modStudent(stuIndex1, room2.getStudents()[stuIndex2]);
			boyRooms[roomIndex2].modStudent(stuIndex2, tempStudent);
		}
		//repeat for girls
		
		for(int i = 0; i<timesMutate; i++) {
			int roomIndex1 = ran.nextInt(3);
			int roomIndex2 = ran.nextInt(3);
			while(roomIndex2==roomIndex1) {
				roomIndex2 = ran.nextInt(3);
			}
			
			room room1 = girlRooms[roomIndex1];
			room room2 = girlRooms[roomIndex2];
			
			int stuIndex1 = ran.nextInt(room1.getStudents().length);
			int stuIndex2 = ran.nextInt(room2.getStudents().length);
			student tempStudent = room1.getStudents()[stuIndex1];
			girlRooms[roomIndex1].modStudent(stuIndex1, room2.getStudents()[stuIndex2]);
			girlRooms[roomIndex2].modStudent(stuIndex2, tempStudent);
		}
	}
	
	//Method to quickly display result, used for testing
	public void outAll(room[] b, room[] g) {
		for(int i = 0; i<3; i++) {
			System.out.println("Boys room " + i);
			pa(b[i].getStudents());
			System.out.println("Fitness: " + b[i].getFit());
			System.out.println("");
			System.out.println("Girls room "+i);
			pa(g[i].getStudents());
			System.out.println("Fitness: " + g[i].getFit());
			System.out.println("");
		}
	}
	
	public void pa(student[] s) {
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i].getName());
		}
	}
	
	//Method to write to file using FileWriter
	public void writeFile(room[] b, room[]g) {
		try {
			String path = System.getProperty("user.home") + "/Desktop" + "/Desktop" + "/roomList.txt";
			File list = new File(path);
			list.createNewFile();
			FileWriter write = new FileWriter(list);
			write.write("List of Rooms: \n");
						for(int i = 0; i<b.length;i++) {
				String output = "\n";
				
				//loop to all rooms, get the name of the students and put it in a string
				//Use length to know the amount of students.
				for (int x = 0; x<b[i].getStudents().length;x++) {
					output += b[i].getStudents()[x].getName() + ", ";
				}
				String w = "Boy room " + (i+1) + ": "+ output + "\n";
				write.write(w);
				
				//Testing outputs
				w = "Fitness of room: " +b[i].getFit() + "\n";
				write.write(w);
				w = "Prefence score: " + b[i].preference + "\n";
				write.write(w);
				w = "Nationalities: " + b[i].nationalities + "\n";
				write.write(w);
				
			}
						
			write.write("\n");
			for(int i = 0; i<g.length;i++) {
				String output = "";
				for (int x = 0; x<g[i].getStudents().length;x++) {
					output += g[i].getStudents()[x].getName() + ", ";
				}
				String w = "Girl room " + (i+1) + ": "+ output + "\n";
				write.write(w);
				w = "Fitness of room: " +g[i].getFit() + "\n";
				write.write(w);
				w = "Prefence score: " + g[i].preference + "\n";
				write.write(w);
				w = "Nationalities: " + g[i].nationalities + "\n";
				write.write(w);
			}
			
			write.write("Total Fitness: " + totalFitness);
			write.flush();
			write.close();
		}
		catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
}
