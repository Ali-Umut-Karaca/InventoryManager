import java.io.File;
import java.net.URISyntaxException;

public class ApplicationParentFolderHandler {
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo 
	 */
    
    // Finds the local folder in the system:
    public static String getExecutionDirectory() {
        try {
            File jarLocation = new File(ApplicationParentFolderHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            
            // Return the folder that contains the executable (Local Folder)
            return jarLocation.getParentFile().getAbsolutePath();
            
        } catch (URISyntaxException e) {
            // Safe fallback just in case
            return System.getProperty("user.dir");
        }
    }
    
    // Get the absolute path of the file program's looking for in the LOCAL folder.
    public static String getLocalFilePath(String fileName) {
        return getExecutionDirectory() + File.separator + fileName;
    }
    // Get inside of the LOCAL_FOLDER/Content/ folder used for accessing files inside of the Content folder
    public static String getLocalContentFile(String fileName) {
        return getExecutionDirectory() + File.separator + "Content" + File.separator +fileName;
    }
}