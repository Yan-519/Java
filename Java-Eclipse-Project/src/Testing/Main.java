package Testing;

import java.util.Random;

public class Main {
	
	public static final Random random = new Random();
	public static final String ALL_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final int len = ALL_STRING.length();
	
	public static String genWinKey() {
		
		String str = "";
		
		for (int i = 0; i < 5; i++) {
			if(i != 0)
				str += "-";
			for (int j = 0; j < 5; j++)
				str += ALL_STRING.charAt(random.nextInt(len));
		}
		
		return str;
	}
	
	
	
	public static void main(String[] args) {

		MySystem system = new MySystem("Production");

        // Call the grouped functions exactly as requested
        system.console.write("System initialized.");
        system.console.write("Loading database...");
        
        // A second system instance maintains separate values
        MySystem backupSystem = new MySystem("Backup");
        backupSystem.console.write("Standing by.");
	}

}
