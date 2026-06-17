import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TXTParser extends DatabaseHandler implements Export{
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo
	 */
	
	/**
	 * @param fileName -> File To Manipulate
	 */
	public TXTParser(String fileName) {
		super(fileName);
	}
	
	/**
	 * @param lineIn -> the lines to write to the text File
	 * @exportLineTXT is a wrapper for BuildExport() method.
	 */
	public void exportLineTXT(String [] lineIn) {
		
		BuildExport(lineIn);
		
	}
	
	/**
	 * @param line -> Line to write to the text file.
	 * @BuildExport -> Wrapper for saveDataToFile method.
	 */
	public void BuildExport(String [] line) {
		//for(int i = 0; i<arr.length;i++) {
			saveDataToFile(fileName,line);
		//	}
		}
	/**
	 * @param fileName -> File to write into.
	 * @param line -> data to write into the file.
	 */
	public void saveDataToFile(String fileName, String [] line) {
		File file = new File(fileName);
		try {
			System.out.println(fileName);
			FileWriter fw = new FileWriter(file,true);
		    fw.write(line[0]+","+line[1]+","+line[2]+","+line[3]+",\n");
		    fw.flush();
		    fw.close();
		} catch (IOException e) {
			System.out.println("Exception occured");
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param fileName -> File to remove line from
	 * @param lineToRemove -> Line String to remove
	 */
	
	
	public static void removeLineFromFile(String fileName, String lineToRemove) {
	    try {
	        Path file = Path.of(fileName);

	        if (!Files.exists(file)) {
	            System.out.println("Parameter is not an existing file");
	            return;
	        }

	        List<String> allLines = Files.readAllLines(file);

	        allLines.removeIf(line -> line.trim().split(",")[0].equals(lineToRemove));

	        Files.write(file, allLines);

	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	/**
	 * @param fileName -> File to update
	 * @param listToUpdate -> Newly created list to update
	 * @Maths -> Deletes all the contents of the file re-writes it with the newly provided list
	 */
	public static void updateLineOnFile(String fileName, List<String[]> listToUpdate){
		try {
	        Path file = Path.of(fileName);

	        if (!Files.exists(file)) {
	            System.out.println("Parameter is not an existing file");
	            return;
	        }

	        List<String> lines = new ArrayList<>();
	        for(int i = 0; i<listToUpdate.size();i++) {
	        	StringBuilder sb = new StringBuilder();
	        	for(int j = 0; j<listToUpdate.get(i).length;j++)
	        		sb.append(listToUpdate.get(i)[j]+",");
	        	lines.add(sb.toString());
	        }
	        	Files.write(file, lines);
	        
	        
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
}
