import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Ali Umut Karaca / MonteCarlo
 * 
 * -This system utilizes many different ways of storage.
 * 		For user satisfaction the main storage is a .csv file.
 * -Just as easily though, you can choose to update your desired Database (SQLite3) with this application.
 * 
 * -The @DatabaseHandler class is mainly here to handle the other storage classes' interactions with the main program.
 * 		Currently, this class is still in developement and only has .csv manipulation methods.
 * 
 */


	public class DatabaseHandler implements Export{
		/**
		 * GLOBAL VARIABLE DECLERATION
		 */
		String fileName="";
		ItemList il = new ItemList();
		
		
		/**
		 * @param fileName -> File to manipulate
		 */
	public DatabaseHandler(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @param fileName -> The .csv file that is going to be read.
	 * @return A two dimentional array of the data that was in the file.
	 */
	
	public String [][] loadDataFromFile(String fileName) {
		
	    List<String[]> rowList = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] splitLine = line.split(",");
	            rowList.add(splitLine);
	        }
	        
	        return rowList.toArray(new String[0][0]);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
    }
	
	/**
	 * @param fileName -> The .csv file that is going to be read.
	 * @return Returns a @Item filled @List Object
	 */
	
	public List <String[]> loadDataFromFileToList(String fileName) {
		
	    List<String[]> rowList = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] splitLine = line.split(",");
	            rowList.add(splitLine);
	        }
	        
	        return rowList;

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
    }
	
	/*
	public void saveDataToFile(String fileName, String [] line) {
		File file = new File(fileName);
		try {
			FileWriter fw = new FileWriter(file,true);
		    fw.write("\n"+line[0]+","+line[1]+","+line[2]+","+line[3]+",");
		    fw.flush();
		    fw.close();
		} catch (IOException e) {
			System.out.println("Exception occured");
			e.printStackTrace();
		}
		
		
	}
	*/
	
	/**
	 * @param Two dimentional array
	 * Writes the contents of the array into the Terminal
	 */
	public static void print2DArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

	
	/**
	 * A must have function because of the Export implementation. 
	 * Will probably be removed in the future
	 */
	@Override
	public void BuildExport() {
		// TODO Auto-generated method stub
		
	}


}
