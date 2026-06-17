import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class StartUpScreen {
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo
	 * This class is responsible for the Environment set-up 
	 * 		that goes on before the Stock Control Program (SCP) initializes.
	 * Gathers needed information for the SCP to properly initiate.
	 */
	
	
	private JFrame frame;
	static String pathsFile = ApplicationParentFolderHandler.getLocalFilePath("paths.txt");
	String DEFAULT_STRING = "ID,NAME,AMOUNT,PRICE,AVERAGE_PRICE\n";
	String FILE_EXTENTION = ".csv";
	static List<String> recentFiles;
	static String currentFile;
	static HashMap <String, String> pathsMap = new HashMap();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartUpScreen window = new StartUpScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartUpScreen() {
		initialize();
		setIcon(ApplicationParentFolderHandler.getLocalContentFile("db.png"));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		List<String> nonExistentPaths = new <String> ArrayList();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Welcome To Inventory Manager");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(109, 163, 233, 20);
		frame.getContentPane().add(comboBox);
		
		//LOAD DATA: Check if file exists and read it
		File historyFile = new File(pathsFile);
		if (historyFile.exists()) {
		    try {
		        // Read all lines into a List
		        recentFiles = Files.readAllLines(Path.of(pathsFile));
		        
		        // Loop through and add them to the box
		        for (String path : recentFiles) {
		    		String splt [] = path.split(Pattern.quote(File.separator));
		    		
		    		pathsMap.put(splt[splt.length-1], path); // MAPPED THE comboBox items to the paths.
		        	
		    		if(Files.exists(Path.of(path))) // Update Logic for Item addition.
		        		comboBox.addItem(splt[splt.length-1]);
		        	else {
		        		comboBox.addItem(splt[splt.length-1]);
		        		nonExistentPaths.add(path);
		        	}
		        }
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		} else {
		    //Create the file if it doesn't exist
		    try { historyFile.createNewFile(); } catch (IOException e) {}
		}

		//Add a placeholder if empty
		if (comboBox.getItemCount() == 0) {
		    comboBox.addItem("No recent projects");
		    comboBox.setEnabled(false); // Disable it so that user can't click it
		}
		
		JLabel lblNewLabel = new JLabel("Welcome to the Inventory Manager");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(99, 27, 253, 105);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("CREATE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser createFilePath = new JFileChooser();
				
				createFilePath.setCurrentDirectory(new File(System.getProperty("user.home")));
		        
		        createFilePath.setDialogTitle("Select a Folder");
		        
		        createFilePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        
		        int result = createFilePath.showOpenDialog(null);
		        
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = createFilePath.getSelectedFile();
                    String userInputName = JOptionPane.showInputDialog("Enter file name");
                    
                    if (userInputName == null || userInputName.trim().isEmpty()) {//CHECK
                        return; 
                    }

                    // 2. Build the initial file path
                    File creatFile = new File(selectedFile.getAbsolutePath() + File.separator + userInputName + FILE_EXTENTION);
                    
                    // 3. The Recurrence Logic
                    if (creatFile.exists()) {
                        int counter = 1;
                        // Keep bumping the number until we find a free name
                        while (creatFile.exists()) {
                            String tempName = userInputName + "(" + counter + ")" + FILE_EXTENTION;
                            creatFile = new File(selectedFile.getAbsolutePath() + File.separator + tempName);
                            counter++;
                        }
                        
                        // Let the user know we adjusted the name
                        JOptionPane.showMessageDialog(frame, "File already exists in this folder!\nCreating new file as: " + creatFile.getName());
                    }
                    
                    // 4. Actually create the file on the drive
                    try {
                        creatFile.createNewFile();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(frame, "Error creating file.");
                        e1.printStackTrace();
		            }
		            		try {
								FileWriter fileInit = new FileWriter(creatFile);
								fileInit.write(DEFAULT_STRING);
								fileInit.close();
								String fileAbsAddress = creatFile.getAbsolutePath();
								System.out.println(fileAbsAddress);
								
								addPath(pathsFile,fileAbsAddress);
								//comboBox.addItem(fileAbsAddress);
								
								String[] splt = fileAbsAddress.split(Pattern.quote(File.separator)); //Change1
							    pathsMap.put(splt[splt.length-1], fileAbsAddress);
							    
							    if (comboBox.getItemCount() > 0 && comboBox.getItemAt(0).equals("No recent projects")) {
							        comboBox.removeAllItems();
							    }
							    comboBox.addItem(splt[splt.length-1]);
							    comboBox.setEnabled(true);
							    
								GroceryStore gs = new GroceryStore(creatFile.getAbsolutePath());
								frame.dispose();
								gs.frame.setVisible(true);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		            		; 
		        } else {
		        	
		        }
				
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(99, 98, 110, 55);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnChoose = new JButton("CHOOSE");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ret="";
					ret = pickFileAddress();
					if(ret.equals(null)){
						System.out.println("Null");
					}
					else {
						try {
							Boolean lineExists = false;
							BufferedReader br = Files.newBufferedReader(Path.of(pathsFile));
							String line = br.readLine();
								while(line != null) {
									if(line.equals(ret)) {
										lineExists = true;
									}
									line = br.readLine();
								}
								
							if(!lineExists) {
								addPath(pathsFile, ret);
							}
							else
								System.out.println("ALREADY EXISTS !");
							br.close();
							
							String[] splt = ret.split(Pattern.quote(File.separator));
						    pathsMap.put(splt[splt.length-1], ret);
						    if (comboBox.getItemCount() > 0 && comboBox.getItemAt(0).equals("No recent projects")) {
						        comboBox.removeAllItems();
						    }
						    comboBox.addItem(splt[splt.length-1]);
						    comboBox.setEnabled(true);
						    
							
						} catch (IOException e1 ) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				
				System.out.println(ret);
				
				GroceryStore gs = new GroceryStore(ret);
				frame.dispose();
				gs.frame.setVisible(true);
				
			}
		});
		btnChoose.setFocusable(false);
		btnChoose.setBounds(232, 98, 110, 55);
		frame.getContentPane().add(btnChoose);
		
		JButton btnOpenRecent = new JButton("Open Recent");
		btnOpenRecent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectedPath = (String) comboBox.getSelectedItem();
				String mappedPath = pathsMap.get(selectedPath);
				
				updatePath(pathsFile, mappedPath);// Add the path to the top of the list
				
				if(!Files.exists(Path.of(mappedPath)) && !mappedPath.equals("No file selected")) {
					JOptionPane.showMessageDialog(frame, selectedPath + " Does Not Exist !");
				}
				else {
			    //check validity
			    if (mappedPath != null && !mappedPath.equals("No file selected")) {
			        System.out.println("Opening recent: " + mappedPath);
			        
			        //Open GUI module for Stock Control Program
			        GroceryStore gs = new GroceryStore(mappedPath);
			        frame.dispose();
			        gs.frame.setVisible(true);
			    }
			    else {
			    	JOptionPane.showMessageDialog(frame, "No File Selected !");
			    	}
				}
			}
		});
		btnOpenRecent.setFocusable(false);
		btnOpenRecent.setBounds(168, 193, 110, 55);
		frame.getContentPane().add(btnOpenRecent);
		
		//invokeLater to wait until the GUI is fully rendered and visible
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        for (int i = 0; i < nonExistentPaths.size(); i++) {
		            int isDelete = JOptionPane.showConfirmDialog(frame, 
		                "The Path is missing for " + 
		                nonExistentPaths.get(i) + 
		                ". Would you like to delete it from the list?");
		            
		            if (isDelete == JOptionPane.YES_OPTION) {
		                removePath(pathsFile, nonExistentPaths.get(i));
		                String[] splt = nonExistentPaths.get(i).split(Pattern.quote(File.separator));
		                String shortName = splt[splt.length - 1];
		                
		                comboBox.removeItem(shortName);
		                pathsMap.remove(shortName);
		            } 
		            else {
		                System.out.println("User Declined to remove Obsolete path.");
		            }
		        }
		    }
		});
	}
	
	private void setIcon(String path) {
		ImageIcon icon = new ImageIcon(path);
        frame.setIconImage(icon.getImage());
	    }
	
	
	public static String pickFileAddress() {
        //Create the File Chooser object:
        JFileChooser chooser = new JFileChooser();

        //Set the starting directory to the home of the user:
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        chooser.setDialogTitle("Select a File");

        // null centers the box
        int result = chooser.showOpenDialog(null);

        //Check for open is clicked or not
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            return selectedFile.getAbsolutePath(); //Return path as String
        } else {
        	//In case of Cancel
            return null;
        }
    }
	
	public static void removePath(String pathsFile, String pathToRemove) {
	    try {
	        Path file = Path.of(pathsFile);
	        
	        if (!Files.exists(file)) {
	            return;
	        }

	        List<String> allLines = Files.readAllLines(file);

	        allLines.remove(pathToRemove);

	        Files.write(file, allLines);

	        System.out.println("Successfully removed obsolete path from history.");

	        recentFiles = Files.readAllLines(Path.of(pathsFile));
	        
	    } catch (IOException e) {
	        System.out.println("Failed to remove path: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static void addPath(String file, String pathToAdd) {
	    try {
	        Path filePath = Path.of(file);
	        
	        if (!Files.exists(filePath)) {
	            Files.createFile(filePath);
	        }
	        
	        List<String> lines = Files.readAllLines(filePath);
	        
	     // 1. Collect all existing filenames into a Set for quick and easy comparison
	        Set<String> existingFileNames = new HashSet<>();
	        for (String line : lines) {
	            if (!line.trim().isEmpty()) {
	                existingFileNames.add(Path.of(line).getFileName().toString());
	            }
	        }

	        // 2. Extract the incoming file's name, parent directory, and extension
	        Path newPath = Path.of(pathToAdd);
	        String originalFileName = newPath.getFileName().toString();
	        
	        // Get the parent path, and add the separator back if it exists
	        String parentDir = newPath.getParent() != null ? newPath.getParent().toString() + File.separator : "";
	        
	        String nameWithoutExt = originalFileName;
	        String ext = "";
	        int dotIndex = originalFileName.lastIndexOf('.');
	        
	        // If the file has an extension, separate it so we can put the (1) before it
	        if (dotIndex > 0) {
	            nameWithoutExt = originalFileName.substring(0, dotIndex);
	            ext = originalFileName.substring(dotIndex); // Includes the dot, e.g., ".txt"
	        }

	        // 3. Find the next available unique name
	        String uniqueFileName = originalFileName;
	        int counter = 1;
	        
	        // Loop continuously until we find a filename that IS NOT in our list
	        while (existingFileNames.contains(uniqueFileName)) {
	            uniqueFileName = nameWithoutExt + "(" + counter + ")" + ext;
	            counter++;
	        }

	        // 4. Reconstruct the full path
	        String finalPathToAdd = parentDir + uniqueFileName;
	        
	        if (counter > 1) { // Only show the dialog if we actually changed the name
	            JOptionPane.showMessageDialog(null, "File path added as: " + finalPathToAdd);
	        }
	        
	        
	        lines.add(0, finalPathToAdd);
	        
	        Files.write(filePath, lines);
	        
	        recentFiles = Files.readAllLines(Path.of(pathsFile));
	    } catch (IOException e) {
	        System.out.println("Failed to write to file: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
	
	public static void updatePath(String pathsFile, String pathToUpdate){
		removePath(pathsFile, pathToUpdate);
		addPath(pathsFile, pathToUpdate);
		
		try {
			recentFiles = Files.readAllLines(Path.of(pathsFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public String findKey(String transfer) {
		Optional<String> foundKey = StartUpScreen.pathsMap.entrySet().stream()
	            .filter(entry -> (transfer).equals(entry.getValue()))
	            .map(Map.Entry::getKey)
	            .findFirst(); // Returns an Optional<String>
		return foundKey.orElse("Can't find Keys");
	}
	
	/*		DEPRECATED METHOD
	 * public void removePath(String paths, String selectedPath) {
		try {
			BufferedReader fr = Files.newBufferedReader(Path.of(paths));
			FileWriter fw = new FileWriter("tmpExclusionPaths.txt",true);
			String buildStr = "";
			String lines = fr.readLine()+"\n";
			buildStr+=lines;
			while(lines != null) {
				if(lines.equals(selectedPath))
					System.out.println("Found the Culprit !");
				else
					buildStr += fr.readLine() +"\n";
					
				lines = fr.readLine();
			}
				System.out.println("Here are the Lines: \n" + buildStr);
			
				
			fw.write(buildStr);
			fr.close();
			fw.close();
			
			Files.delete(Path.of(paths));
			Files.copy(Path.of("tmpExclusionPaths.txt"), Path.of(paths));
			Files.delete(Path.of("tmpExclusionPaths.txt"));
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}*/
	
	public void handleCreateButton(String selectedDirectory, String desiredFileName) {
	    try {
	        File newFile = new File(selectedDirectory, desiredFileName);
	        boolean nameChanged = false;

	        // 1. Check if the physical file already exists in this folder
	        if (newFile.exists()) {
	            nameChanged = true;
	            String nameWithoutExt = desiredFileName;
	            String ext = "";
	            int dotIndex = desiredFileName.lastIndexOf('.');
	            
	            if (dotIndex > 0) {
	                nameWithoutExt = desiredFileName.substring(0, dotIndex);
	                ext = desiredFileName.substring(dotIndex);
	            }

	            int counter = 1;
	            // 2. Loop until we find a filename that doesn't exist on the drive
	            while (newFile.exists()) {
	                String tempName = nameWithoutExt + "(" + counter + ")" + ext;
	                newFile = new File(selectedDirectory, tempName);
	                counter++;
	            }
	        }

	        // 3. Actually create the physical file
	        if (newFile.createNewFile()) {
	            if (nameChanged) {
	                JOptionPane.showMessageDialog(null, 
	                    "A file with that name already existed.\nCreated new file as: " + newFile.getName(), 
	                    "File Renamed", JOptionPane.INFORMATION_MESSAGE);
	            }
	            
	            // 4. Send the clean, absolute path to your (reverted) addPath method
	            addPath(pathsFile, newFile.getAbsolutePath()); 
	            
	            // Proceed to open/load the file into your app...
	        }

	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Error creating file: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void handleChooseButton() {
	    JFileChooser fileChooser = new JFileChooser();
	    int result = fileChooser.showOpenDialog(null); // Or pass your main frame

	    if (result == JFileChooser.APPROVE_OPTION) {
	        File chosenFile = fileChooser.getSelectedFile();
	        
	        if (chosenFile.exists()) {
	            // 1. Send the clean, absolute path straight to the text file
	            addPath(pathsFile, chosenFile.getAbsolutePath());
	            
	            // Proceed to open/load the file into your app...
	        }
	    }
	}
}
