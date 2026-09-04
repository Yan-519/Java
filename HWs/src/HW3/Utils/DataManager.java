package HW3.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

//import HW3.DeliveryDataBase;
//import HW3.DataObjects.*;
import HW3.DataObjects.Helpers.*;
import HW3.Exceptions.*;

public class DataManager {
	private static final String DIR = "src" + File.separator + "HW3" + File.separator + "DataFiles";
	
	// Load class c from memory
	public static <T extends StringConvertertable<T>> ArrayList<ConvertorHolder<? extends T>> load(Class<T> c) 
			throws Exception{
		String fileName = c.getSimpleName().toUpperCase() + ".txt";
		
		File file = new File(DIR, fileName);
		
		if(!file.exists())
			throw new TargetObjectDoesntExistException(file.getPath());
		
		ArrayList<ConvertorHolder<? extends T>> res = new ArrayList<>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		try {

			T empty = c.getDeclaredConstructor().newInstance();
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				ConvertorHolder<? extends T> t = empty.convert(line);
				if(t == null)
					throw new TargetObjectDoesntExistException(line);
				res.add(t);
				
			}
		} finally {
			bufferedReader.close();
		}
		return res;
	}

	// save class c (the array) to memory
	public static <T extends StringConvertertable<T>> void save(ArrayList<T> arr, Class<T> c) throws Exception {
		String fileName = c.getSimpleName().toUpperCase() + ".txt";
		
		File file = new File(DIR, fileName);
		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(file));
		
		try {
			for (T t : arr) {
				if(t== null) 
					throw new TargetObjectDoesntExistException(c.getSimpleName());
				bufferedWriter.write(t.convert());
				bufferedWriter.newLine();
			}
		} finally {
			bufferedWriter.close();
		}
	}
	
}
