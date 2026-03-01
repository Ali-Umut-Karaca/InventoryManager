import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
