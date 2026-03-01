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
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class StartUpScreen {
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo
	 * This class is resposible for the Environment set-up 
	 * 		that goes on before the Stock Control Program (SCP) initializes.
	 * Gathers needed information for the SCP to properly initiate.
	 */
	
	
	private JFrame frame;
	private String pathsFile = ApplicationParentFolderHandler.getLocalFilePath("paths.txt");

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
		        List<String> recentFiles = Files.readAllLines(Path.of(pathsFile));
		        
		        // Loop through and add them to the box
		        for (String path : recentFiles) {
		        	if(Files.exists(Path.of(path)))
		        		comboBox.addItem(path);
		        	else {
		        		comboBox.addItem(path);
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
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(99, 98, 110, 55);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnChoose = new JButton("CHOOSE");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(pathsFile);
				String ret="";
					ret = pickFileAddress();
					if(ret.equals("null")){
						System.out.println("Null");
					}
					else {
						try {
							Boolean lineExists = false;
							FileWriter fw = new FileWriter(file, true);
							BufferedReader br = Files.newBufferedReader(Path.of(pathsFile));
							String line = br.readLine();
							
								while(line != null) {
									if(line.equals(ret)) {
										lineExists = true;
									}
									line = br.readLine();
								}
								
							if(!lineExists)
								fw.write(ret + "\n");
							else
								System.out.println("ALREADY EXISTS !");
							br.close();
							
							fw.close();
						} catch (IOException e1) {
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
			    
				if(!Files.exists(Path.of(selectedPath)) && !selectedPath.equals("No file selected")) {
					JOptionPane.showMessageDialog(frame, selectedPath + " Does Not Exist !");
				}
				else {
			    //check validity
			    if (selectedPath != null && !selectedPath.equals("No file selected")) {
			        System.out.println("Opening recent: " + selectedPath);
			        
			        //Open GUI module for Stock Control Program
			        GroceryStore gs = new GroceryStore(selectedPath);
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
		                comboBox.removeItem(nonExistentPaths.get(i));
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
	
	public void removePath(String paths, String selectedPath) {
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
	}
}
