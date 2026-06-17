import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
  * @author Ali Umut Karaca / MonteCarlo
  * 
  */

public class GroceryStore {
/**
  * 	GLOBAL VARIABLE DECLERATIONS
  */
	public JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mnıtmNewMenuItem;
	private JMenuItem mnıtmNewMenuItem_1;
	private String fileName = "";//"Stocks.txt";
	private JMenuItem mnıtmNewMenuItem_2;
	private boolean modifyDatabase = false;
	private JMenu mnNewMenu_1;
	private JCheckBoxMenuItem checkBox;
	private ItemList itemList = new ItemList();
	private DatabaseHandler db;
	private JMenu mnNewMenu_2;
	private JMenuItem mnıtmNewMenuItem_3;
	private XMLParser xmlExport;
	private TXTParser txtExport;
	private JPopupMenu popupMenu;
	private JMenu mnNewMenu_3;
	private JMenuItem mnıtmNewMenuItem_4;
	private JMenuItem mnıtmNewMenuItem_5;
	private JMenuItem mnıtmNewMenuItem_6;
	private JMenuItem mnıtmNewMenuItem_7;
	private JMenu mnNewMenu_4;
	private JMenuItem mnıtmNewMenuItem_8;
	private JButton BackButtonMenuBar;
	private ArrayList<Integer> targetColIndex = new ArrayList<Integer>();
	private Color targetColColor = null;
	private ArrayList<Integer> targetRowIndex = new ArrayList<Integer>();
	private Color targetRowColor = null;
	private JMenu mnNewMenu_5;
	private JMenuItem mnıtmNewMenuItem_9;
	private JMenuItem mnıtmNewMenuItem_10;
	private JMenuItem mnıtmNewMenuItem_11;
	private JMenuItem mnıtmNewMenuItem_12;
	private boolean colorCol = false;
	private boolean colorRow = false;
	Color lightRed = new Color(255,200,200);
	SQLHandler sqlHandler = new SQLHandler();
	private String lastSearchedType = "NONE"; // Can be "ID", "NAME", "AMOUNT", "PRICE"
	private int lastSearchedID = -1;
	private String lastSearchedName = "";
	private int lastSearchedAmount = -1;
	private double lastSearchedPrice = -1.0;
	private JLabel Info;
	private List<String[]> databaseList;
	private JMenuItem mnıtmNewMenuItem_13;
	private JMenu mnNewMenu_6;
	private JMenuItem mnıtmNewMenuItem_15;
	private JMenuItem mnıtmNewMenuItem_16;
	private JMenuItem mnıtmNewMenuItem_17;
	private JMenu mnNewMenu_7;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroceryStore window = new GroceryStore();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	/**
	 * CREATE THE APPLICATION
	 * @param fileName -> FILE TO BE MANIPULATED
	 */
	public GroceryStore(String fileName) { 
/**
  * 	 	We specifically instantiate the fileName variable here.
  * 		This is because we need all the modules to talk to the same file.
  */
			this.fileName = fileName;
			this.db = new DatabaseHandler(fileName);
			this.xmlExport = new XMLParser(fileName);
			this.txtExport = new TXTParser(fileName);
			databaseList = db.loadDataFromFileToList(fileName);
			initialize();
			setIcon(ApplicationParentFolderHandler.getLocalContentFile("db.png"));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 601, 461);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Inventory Manager - " + fileName);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		
		mnNewMenu_3 = new JMenu("Sort By->");
		popupMenu.add(mnNewMenu_3);
		
		mnıtmNewMenuItem_4 = new JMenuItem("NAME");
		mnıtmNewMenuItem_4.addActionListener(new ActionListener() {
			public void  actionPerformed(ActionEvent e) {
				highlightColumn(1, lightRed);
				loadNewFrameFromItemList(itemList.alphabeticOrder());
				Info.setText("Sort by -> NAME");
			}
		});
		
		mnıtmNewMenuItem_5 = new JMenuItem("ID");
		mnıtmNewMenuItem_5.addActionListener(new ActionListener() {
			public void  actionPerformed(ActionEvent e) {
				highlightColumn(0, lightRed);
				loadNewFrameFromItemList(itemList.idOrder(""));
				Info.setText("Sort by -> ID");
			}
		});
		
		mnNewMenu_3.add(mnıtmNewMenuItem_5);
		mnNewMenu_3.add(mnıtmNewMenuItem_4);
		
		mnıtmNewMenuItem_6 = new JMenuItem("AMOUNT");
		mnıtmNewMenuItem_6.addActionListener(new ActionListener() {
			public void  actionPerformed(ActionEvent e) {
				highlightColumn(2, lightRed);
				loadNewFrameFromItemList(itemList.amountOrder());
				Info.setText("Sort by -> AMOUNT");
			}
		});
		
		mnNewMenu_3.add(mnıtmNewMenuItem_6);
		
		mnıtmNewMenuItem_7 = new JMenuItem("PRICE");
		mnıtmNewMenuItem_7.addActionListener(new ActionListener() {
			public void  actionPerformed(ActionEvent e) {
				highlightColumn(3, lightRed);
				loadNewFrameFromItemList(itemList.priceOrder());
				Info.setText("Sort by -> PRICE");
			}
		});
		mnNewMenu_3.add(mnıtmNewMenuItem_7);
		
		mnNewMenu_4 = new JMenu("Filter By ->");
		popupMenu.add(mnNewMenu_4);
		
		mnıtmNewMenuItem_8 = new JMenuItem("Price");
		mnıtmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHighlights();
				double exp = Double.parseDouble(JOptionPane.showInputDialog("Please Enter the Price you want to see Under: "));
				BackButtonMenuBar.setVisible(true);
				
				lastSearchedType = "FILTER_PRICE";
	            lastSearchedPrice = exp;
	            
				loadNewFrameFromItemList(itemList.filterItemsByPrice(exp));
				Info.setText("Filter By -> PRICE {▼"+exp+"$}");
				
				for (int i = 0; i < model.getRowCount(); i++) {
	                highlightRowNoReset(i, new Color(255, 200, 200));
	            }
			}
			
		});
		
		mnNewMenu_4.add(mnıtmNewMenuItem_8);
		
		mnNewMenu_5 = new JMenu("Find");
		popupMenu.add(mnNewMenu_5);
		
		mnıtmNewMenuItem_9 = new JMenuItem("With ID");
		mnıtmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHighlights();
		        try {
		            String input = JOptionPane.showInputDialog("Enter the ID");
		            Info.setText("Find -> ID: "+"{"+input+"}");
		            if (input == null) return;
		            int find = Integer.parseInt(input);

		            lastSearchedType = "ID";
		            lastSearchedID = find;

		            enableBackButton();
		            loadNewFrameFromItemList(itemList.findByID(find));

		            if (model.getRowCount() > 0) {
		                highlightRow(0, new Color(255, 200, 200));
		            }
		        } catch (NumberFormatException k) {
		            System.out.println("Process Exited {Find->WithID}");
		        }
		    }

		});
		mnNewMenu_5.add(mnıtmNewMenuItem_9);
		
		mnıtmNewMenuItem_10 = new JMenuItem("With Name");
		mnıtmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHighlights();
		        try {
		            String find = JOptionPane.showInputDialog("Enter the name");
		            Info.setText("Find -> NAME: "+"{"+find+"}");
		            if (find == null) return; // Handle cancel button

		            lastSearchedType = "NAME";
		            lastSearchedName = find;

		            enableBackButton();
		            loadNewFrameFromItemList(itemList.findByName(find));

		            for (int i = 0; i < model.getRowCount(); i++) {
		                highlightRowNoReset(i, new Color(255, 200, 200));
		            }
		        } catch (NullPointerException k) {
		            System.out.println("Process Exited {Find->WithName}");
		        }
		    }
		});
		mnNewMenu_5.add(mnıtmNewMenuItem_10);
		
		mnıtmNewMenuItem_11 = new JMenuItem("With Amount");
		mnıtmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHighlights();
		        try {
		            String input = JOptionPane.showInputDialog("Enter the amount");
		            Info.setText("Find -> Amount: "+"{"+input+"}");
		            if (input == null) return;
		            int find = Integer.parseInt(input);

		            //Save Memory
		            lastSearchedType = "AMOUNT";
		            lastSearchedAmount = find;

		            //Filter View
		            enableBackButton();
		            loadNewFrameFromItemList(itemList.findByAmount(find));

		            //Highlight all rows in the filtered view
		            for (int i = 0; i < model.getRowCount(); i++) {
		                highlightRowNoReset(i, new Color(255, 200, 200));
		            }
		        } catch (NumberFormatException k) {
		            System.out.println("Process Exited {Find->WithAmount}");
		        }
		    }
			
		});
		mnNewMenu_5.add(mnıtmNewMenuItem_11);
		
		mnıtmNewMenuItem_12 = new JMenuItem("With Price");
		mnıtmNewMenuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetHighlights();
		        try {
		            String input = JOptionPane.showInputDialog("Enter the Price");
		            Info.setText("Find -> PRICE: "+"{"+input+"}");
		            if (input == null) return;
		            double find = Double.parseDouble(input);

		            //Save Memory
		            lastSearchedType = "PRICE";
		            lastSearchedPrice = find;

		            //Filter View
		            enableBackButton();
		            loadNewFrameFromItemList(itemList.findByPrice(find));

		            //Highlight all rows in the filtered view
		            for (int i = 0; i < model.getRowCount(); i++) {
		                highlightRowNoReset(i, new Color(255, 200, 200));
		            }
		        } catch (NumberFormatException k) {
		            System.out.println("Process Exited {Find->WithPrice}");
		        }
		    }
			
		});
		mnNewMenu_5.add(mnıtmNewMenuItem_12);
		
		table = new JTable() {
			@Override
		    public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);

		        //Determine if we should highlight the cell
		        boolean shouldHighlight = false;

		        if (colorCol && !colorRow) {
		            //Check if 'col' exists in the list
		            if (targetColIndex.contains(col)) {
		                shouldHighlight = true;
		            }
		        } else if (colorRow && !colorCol) {
		            //Check if 'row' exists in your list
		            if (targetRowIndex.contains(row)) {
		                shouldHighlight = true;
		            }
		        }

		        //Apply the color based on the result
		        if (shouldHighlight) {
		            //Use the row color or col color depending on mode
		            if (colorRow) comp.setBackground(targetRowColor);
		            else comp.setBackground(targetColColor);
		        } else {
		            //Standard Reset (Must happen if NOT highlighting)
		            if (isCellSelected(row, col)) {
		                comp.setBackground(getSelectionBackground());
		            } else {
		                comp.setBackground(Color.WHITE);
		            }
		        }
		        return comp;
		    }
			
		};
		scrollPane.setViewportView(table);
		table.setModel(model=new DefaultTableModel());
		
		mnıtmNewMenuItem_15 = new JMenuItem("New menu item");
		scrollPane.setColumnHeaderView(mnıtmNewMenuItem_15);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnNewMenu_6 = new JMenu("File");
		menuBar.add(mnNewMenu_6);
		
		mnıtmNewMenuItem_17 = new JMenuItem("Open");
		mnıtmNewMenuItem_17.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
		});
		mnNewMenu_6.add(mnıtmNewMenuItem_17);
		
		mnNewMenu_7 = new JMenu("Open Recent ");
		mnNewMenu_6.add(mnNewMenu_7);
		
		//POPULATE RECENT FILES
		List <String> transfer = StartUpScreen.recentFiles;
		for(int i = 0; i<transfer.size(); i++) {
			
			if(i>=5) break; //Just the first 5 elements
			
			JMenuItem fillMenu = new JMenuItem(StartUpScreen.findKey(transfer.get(i)));
			fillMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					fileName = StartUpScreen.pathsMap.get(fillMenu.getText());
					db = new DatabaseHandler(fileName);
					xmlExport = new XMLParser(fileName);
					txtExport = new TXTParser(fileName);
					databaseList = db.loadDataFromFileToList(fileName);
					StartUpScreen.updatePath(StartUpScreen.pathsFile, fileName);
					loadFrameZeroState();
				}
			});
			mnNewMenu_7.add(fillMenu);
		}
		//END POPULATION
		
		mnıtmNewMenuItem_16 = new JMenuItem("Delete File");
		mnıtmNewMenuItem_16.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						int confirmation = JOptionPane.NO_OPTION;
						confirmation = JOptionPane.showConfirmDialog(frame, 
								"Are you sure you would like to delete"+"'"+fileName+"' ?");
						
						if(confirmation == JOptionPane.OK_OPTION) {
							try {
								Files.delete(Path.of(fileName));
								StartUpScreen.removePath(StartUpScreen.pathsFile, fileName);
								frame.dispose();
								StartUpScreen.main(null);
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(frame,
										"Failed to Delete file "+"'"+fileName +"'"+" Closing...");
								System.out.println("Failed to Delete file "+"'"+fileName +"'"+" Closing...");
								e1.printStackTrace();
							}
							
							
						}else if(confirmation == JOptionPane.NO_OPTION)
							System.out.println("I have been No'd!");
						else if(confirmation == JOptionPane.CANCEL_OPTION)
							System.out.println("I have been Canceled !");
					}
				}
			);
		
		mnNewMenu_6.add(mnıtmNewMenuItem_16);
		
		mnNewMenu = new JMenu("Operations");
		menuBar.add(mnNewMenu);
		
		mnıtmNewMenuItem = new JMenuItem("Get Inventory Worth");
		mnıtmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTotalWorth();
			}
		});
		
		mnıtmNewMenuItem_1 = new JMenuItem("Add New Item");
		mnıtmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String [] str = new String[model.getColumnCount()-1];
				boolean problem = false;
				
				for(int col = 0; col<model.getColumnCount()-1;col++) {
					problem = false;
					String inP = JOptionPane.showInputDialog("Enter "+model.getColumnName(col)+": ");
					if(inP == null) {
						problem = true;
						break;
					}
					if(col == 0) {
						try{
							int test = Integer.parseInt(inP);
							
							for(int i = 1; i<databaseList.size(); i++) {
								if(Integer.parseInt(databaseList.get(i)[0]) == test) {
									problem = true;
									JOptionPane.showMessageDialog(frame, "CAN'T HAVE DUPLICATE ID");
									col--;
									continue;
								}
								else {
								}
							}
							
						}
						catch(NumberFormatException e1) {
							problem = true;
							JOptionPane.showMessageDialog(frame, "Please Enter An Integer. ID");
							col--;
							continue;
						}
					}
					else if(col == 2) {
						try{
							Integer.parseInt(inP);
						}
						catch(NumberFormatException e1) {
							problem = true;
							JOptionPane.showMessageDialog(frame, "Please Enter An Integer.");
							col--;
							continue;
						}
					}
					else if(col == 3) {
						try{
							Double.parseDouble(inP);
						}
						catch(NumberFormatException e1) {
							problem = true;
							JOptionPane.showMessageDialog(frame, "Please Enter A Number.");
							col--;
							continue;
						}
					}
					
					if(col == 3) {
						Double.parseDouble(inP);
					}
					
						
					
					if(!problem)
						str[col] = inP;
					
				}
				
				if(modifyDatabase && !problem) {
					txtExport.exportLineTXT(str);
					sqlHandler.writeRow(str);
				}
				if(!problem) {
					itemList.add(str);
					databaseList.add(str); // Add to the original list as well.
					str[3] += " $";
					model.addRow(str);
				}
			}
			
		}
				
				
				
				);
		mnNewMenu.add(mnıtmNewMenuItem_1);
		
		mnıtmNewMenuItem_2 = new JMenuItem("Remove Menu Item");
		mnıtmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog("Enter ID of the item to remove");
				for(int row = 0; row<model.getRowCount(); row++) {
					itemList.printList();
					if (model.getValueAt(row, 0).toString().equals(str)) {
						model.removeRow(row);
						itemList.list.remove(row);
						
						databaseList.removeIf(a -> a[0].equals(str));
						
						databaseList.forEach(a -> System.out.println(Arrays.toString(a))); // Print databaseList
						itemList.printList();
					}
				}
				if(modifyDatabase) {
					TXTParser.removeLineFromFile(fileName,str);
					sqlHandler.deleteRow(Integer.parseInt(str));
				}
			}
		});
		
		mnıtmNewMenuItem_13 = new JMenuItem("Update Item by ID");
		mnıtmNewMenuItem_13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String id = JOptionPane.showInputDialog("Enter the Item ID to Update");
				
				String [] str = new String[model.getColumnCount()-1];
				boolean problem = false;
				
				for(int col = 0; col<model.getColumnCount()-1;col++) {
					problem = false;
					String inP = JOptionPane.showInputDialog("Enter "+model.getColumnName(col)+": ");
					if(inP == null) {
						problem = true;
						break;
					}
					if(col == 0) {
						try{
							Integer.parseInt(inP);
						}
						catch(NumberFormatException e1) {
							problem = true;
							JOptionPane.showMessageDialog(frame, "Please Enter An Integer. ID");
							col--;
							continue;
						}
					}
					else if(col == 2) {
						try{
							Integer.parseInt(inP);
						}
						catch(NumberFormatException e1) {
							problem = true;
							JOptionPane.showMessageDialog(frame, "Please Enter An Integer.");
							col--;
							continue;
						}
					}
					else if(col == 3) {
						try{
							Double.parseDouble(inP);
						}
						catch(NumberFormatException e1) {
							problem = true;
							JOptionPane.showMessageDialog(frame, "Please Enter A Number.");
							col--;
							continue;
						}
					}
					
					if(col == 3) {
						Double.parseDouble(inP);
					}
					
						
					
					if(!problem)
						str[col] = inP;
					
				}
				databaseList.removeIf(a -> a[0].equals(str[0])); // Delete the matching text from the existing database List
				databaseList.add(str); // Add the newly created string to the list
				loadNewFrameFromItemList(itemList.updateItemInList(str).idOrder());
				
			if(modifyDatabase == true) {
				//TODO: Add the necessary parts to handle the actual file writing operations.
					//TODO: Do this by writing a switch or update function in the right class and call it here.
				TXTParser.updateLineOnFile(fileName, databaseList);
			}
			
			}
		});
		mnNewMenu.add(mnıtmNewMenuItem_13);
		mnNewMenu.add(mnıtmNewMenuItem_2);
		mnNewMenu.add(mnıtmNewMenuItem);
		
		mnNewMenu_1 = new JMenu("Manipulation Selection");
		menuBar.add(mnNewMenu_1);
		
		checkBox = new JCheckBoxMenuItem("Modify Database");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkBox.isSelected())
					modifyDatabase = true;
				else
					modifyDatabase = false;
				
			}
			
			
		});
		mnNewMenu_1.add(checkBox);
		
		mnNewMenu_2 = new JMenu("Export");
		menuBar.add(mnNewMenu_2);
		
		mnıtmNewMenuItem_3 = new JMenuItem("Export XML");
		mnıtmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				xmlExport.BuildExport();
				
			}
		});
		
		mnNewMenu_2.add(mnıtmNewMenuItem_3);
		
		BackButtonMenuBar = new JButton("<- (Back)");
		BackButtonMenuBar.setFont(new Font("Tahoma", Font.BOLD, 10));
		BackButtonMenuBar.setFocusPainted(false);
		BackButtonMenuBar.setContentAreaFilled(false);
		BackButtonMenuBar.setBorderPainted(false);
		BackButtonMenuBar.setOpaque(true);
		BackButtonMenuBar.setVisible(false);
		//BackButtonMenuBar.add(Box.createHorizontalGlue());
		//BackButtonMenuBar.setContentAreaFilled(true);
		//BackButtonMenuBar.
		BackButtonMenuBar.setBorder(BorderFactory.createMatteBorder(5, 50, 5, 50, Color.white));
		menuBar.add(BackButtonMenuBar);
		menuBar.add(Box.createHorizontalGlue());
		BackButtonMenuBar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPressBackButton();
			}
		});
		
		Color normalBg = new Color(243,243,243);//BackButtonMenuBar.getParent().getBackground();// transparent
		Color hoverBg  = new Color(220, 220, 220); // light gray (adjust)
		BackButtonMenuBar.setBackground(normalBg);
		
		Info = new JLabel("");
		Info.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12));
		Info.setHorizontalAlignment(SwingConstants.CENTER);
		Info.setHorizontalTextPosition(SwingConstants.LEFT);
		menuBar.add(Info);
		BackButtonMenuBar.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	BackButtonMenuBar.setBackground(hoverBg);
		    }
		    public void mouseExited(MouseEvent e) {
		    	BackButtonMenuBar.setBackground(normalBg);
		    }
		});

		
		
		//db.print2DArray(db.loadDataFromFile(fileName));
		loadFrame();
		loadNewFrameFromItemList(itemList.idOrder());
		highlightColumn(0, new Color(255, 200, 200));
		//System.out.println(itemList.list.size());
		
		/*
		itemList.alphabeticOrder();
		System.out.println();
		itemList.priceOrder();
		System.out.println();
		itemList.amountOrder();
		itemList.getAverage("Lambda");
		*/
	}
	
	public void highlightColumn(int colIndex, Color color) {
	    //Update the state variables
		resetHighlights();
	    this.targetColIndex.add(colIndex);
	    this.targetColColor = color;
	    this.colorCol = true;
	    this.colorRow = false;
		
	    //Force the table to refresh
	    table.repaint(); 
	}
	
	public void highlightColumnNoReset(int colIndex, Color color) {
	    //Update the state variables
	    this.targetColIndex.add(colIndex);
	    this.targetColColor = color;
	    this.colorCol = true;
	    this.colorRow = false;
		
	    //Force the table to refresh
	    table.repaint(); 
	}
	
	public void highlightRow(int rowIndex, Color color) {
	    //Update the state variables
		resetHighlights();
	    this.targetRowIndex.add(rowIndex);
	    this.targetRowColor = color;
	    this.colorRow = true;
	    this.colorCol = false;
		
	    //Force the table to refresh
	    table.repaint(); 
	}
	
	public void highlightRowNoReset(int rowIndex, Color color) {
	    // 1. Update the state variables
	    this.targetRowIndex.add(rowIndex);
	    this.targetRowColor = color;
	    this.colorRow = true;
	    this.colorCol = false;
		
	    // 2. Force the table to refresh
	    table.repaint(); 
	}
	
	public void resetHighlights() {
	    //Clear the lists so they are empty for the next job
	    targetRowIndex.clear();
	    targetColIndex.clear();

	    //Reset the flags
	    colorRow = false;
	    colorCol = false;

	    //Reset colors to null if you want to be extra safe
	    targetRowColor = null;
	    targetColColor = null;

	    // Since the lists are now empty, prepareRenderer will paint everything white
	    table.repaint();
	}
	
	public void enableBackButton() {
		BackButtonMenuBar.setVisible(true);
	}
	
	public void onPressBackButton() {
		resetHighlights();
		model.setRowCount(0);
		itemList.list.clear();
		loadFrame();
		BackButtonMenuBar.setVisible(false);
		//CheckPoint 1
		if (lastSearchedType.equals("ID")) {
	        int index = findPosition(lastSearchedID);
	        if (index != -1) 
	        	highlightRow(index, new Color(255, 200, 200));
	    } 
		else if (lastSearchedType.equals("NAME")) {
	        for (int i = 0; i < model.getRowCount(); i++) {
	            if (model.getValueAt(i, 1).toString().equalsIgnoreCase(lastSearchedName)) {
	                highlightRowNoReset(i, new Color(255, 200, 200));
	                
	            }
	        }
	        
	       
	    } else if (lastSearchedType.equals("AMOUNT")) {
	        for (int i = 0; i < model.getRowCount(); i++) {
	            int val = Integer.parseInt(model.getValueAt(i, 2).toString());
	            if (val == lastSearchedAmount) {
	                highlightRowNoReset(i, new Color(255, 200, 200));
	            }
	        }

	    } else if (lastSearchedType.equals("PRICE")) {
	        for (int i = 0; i < model.getRowCount(); i++) {
	            String raw = model.getValueAt(i, 3).toString().split(" ")[0];
	            double val = Double.parseDouble(raw);
	            if (val == lastSearchedPrice) {
	                highlightRowNoReset(i, new Color(255, 200, 200));
	            }
	        }
	    }
	    else if (lastSearchedType.equals("FILTER_PRICE")) {
	        for (int i = 0; i < model.getRowCount(); i++) {
	            try {
	                String raw = model.getValueAt(i, 3).toString().split(" ")[0];
	                double val = Double.parseDouble(raw);
	                
	                if (val < lastSearchedPrice) {
	                    highlightRowNoReset(i, new Color(255, 200, 200));
	                }
	            } catch (NumberFormatException e) { }
	        }
	    }
	    lastSearchedType = "NONE";
	}
	
	public void loadNewFrameFromItemList(ItemList li) {
		model.setRowCount(0);
		itemList.list.clear();
		String [][] dStr = li.listToDoubleArray();
		boolean firstLine = true;
		for(int i = 0;i<dStr.length;i++) {
			if(firstLine) {
				model.setColumnIdentifiers(dStr[0]);
				firstLine = false;
				
			}
			else {
				//POPULATE THE LIST:
            	itemList.add(new Item(Integer.parseInt(dStr[i][0]),
            			dStr[i][1],
            			Integer.parseInt(dStr[i][2]),
            			Double.parseDouble(dStr[i][3])
            			).setAveragePrice(dStr[i].length > 4 ? Double.parseDouble(dStr[i][4]) : 0));
            	
            	dStr[i][3] +=" $";
            	
            	if(dStr[i].length > 4 )
            		dStr[i][4] += " $";
            	
            	model.addRow(dStr[i]);
			}
		}
		/*
		itemList.printList();
		System.out.println(itemList.getAverage());
		System.out.println(itemList.getAverage("Lambda"));
		*/
	}

	public void loadFrame() {
		String [][] dStr = db.loadDataFromFile(fileName);
		boolean firstLine = true;
		for(int i = 0;i<dStr.length;i++) {
			if(firstLine) {
				model.setColumnIdentifiers(dStr[0]);
				firstLine = false;
				
			}
			else {
				//POPULATE THE LIST:
            	itemList.add(new Item(Integer.parseInt(dStr[i][0]),
            			dStr[i][1],
            			Integer.parseInt(dStr[i][2]),
            			Double.parseDouble(dStr[i][3])
            			).setAveragePrice(dStr[i].length > 4 ? Double.parseDouble(dStr[i][4]) : 0));
            	
            	dStr[i][3] +=" $";
            	
            	if(dStr[i].length > 4 )
            		dStr[i][4] += " $";
            	
            	model.addRow(dStr[i]);
			}
		}
		itemList.printList();
		/*System.out.println(itemList.getAverage());
		System.out.println(itemList.getAverage("Lambda"));
		*/
		System.out.println("DB:");
		try {
			sqlHandler.printTable();
			//sqlHandler.writeRow(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFrameZeroState() {
		
		//S:
		mnNewMenu_7.removeAll(); // Remove Old List
		List <String> transfer = StartUpScreen.recentFiles;
		for(int i = 0; i<transfer.size(); i++) {
			
			if(i>=5) break; //Just the first 5 elements
			
			JMenuItem fillMenu = new JMenuItem(StartUpScreen.findKey(transfer.get(i)));
			fillMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					fileName = StartUpScreen.pathsMap.get(fillMenu.getText());
					db = new DatabaseHandler(fileName);
					xmlExport = new XMLParser(fileName);
					txtExport = new TXTParser(fileName);
					databaseList = db.loadDataFromFileToList(fileName);
					StartUpScreen.updatePath(StartUpScreen.pathsFile, fileName);
					loadFrameZeroState();
				}
			});
			mnNewMenu_7.add(fillMenu);
		}
		//E:
		
		//Set the Texts to empty or default.
		Info.setText("");
		BackButtonMenuBar.doClick(); // Set Search State to default action.
		BackButtonMenuBar.setVisible(false);
		//
		databaseList = db.loadDataFromFileToList(fileName); //LOAD DEFAULT IMAGE ONTO databaseList
		itemList.list.clear();
		String [][] dStr = db.loadDataFromFile(fileName);
		boolean firstLine = true;
		for(int i = 0;i<dStr.length;i++) {
			if(firstLine) {
				model.setColumnIdentifiers(dStr[0]);
				firstLine = false;
				
			}
			else {
				//POPULATE THE LIST:
            	itemList.add(new Item(Integer.parseInt(dStr[i][0]),
            			dStr[i][1],
            			Integer.parseInt(dStr[i][2]),
            			Double.parseDouble(dStr[i][3])
            			).setAveragePrice(dStr[i].length > 4 ? Double.parseDouble(dStr[i][4]) : 0));
            	
            	dStr[i][3] +=" $";
            	
            	if(dStr[i].length > 4 )
            		dStr[i][4] += " $";
            	
            	model.addRow(dStr[i]);
			}
		}
		
		loadNewFrameFromItemList(itemList.idOrder());
		highlightColumn(0, new Color (255,200,200));
		itemList.printList();
		System.out.println(itemList.getAverage());
		System.out.println(itemList.getAverage("Lambda"));
		
		System.out.println("DB:");
		//sqlHandler.loadUpdatedData(itemList);
		//sqlHandler.writeRow(itemList.listToDoubleArray()[15]);
	}
	
	//TODO: Move this Method to the DatabaseHandler or the TXT Parser classes and make it static for use in everywhere.
		//DONE !
	
	//DEPRECATED METHOD REMOVE IN STABLE RELEASE|||
	/*public void removeLineFromFile(String fileName, String lineToRemove) {
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
	}*/
	
	
	/*		DEPRECATED METHOD
	    public void removeLineFromFile(String fileName, String lineToRemove) {
	    	
	    try {

	      File inFile = new File(fileName);

	      if (!inFile.isFile()) {
	        System.out.println("Parameter is not an existing file");
	        return;
	      }

	      //Construct the new file that will later be renamed to the original filename.
	      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

	      BufferedReader br = new BufferedReader(new FileReader(fileName));
	      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

	      String line = null;

	      //Read from the original file and write to the new
	      //unless content matches data to be removed.
	      while ((line = br.readLine()) != null) {

	        if (!line.trim().split(",")[0].equals(lineToRemove)) {
	          pw.println(line);
	          pw.flush();
	        }
	        
	      }
	      pw.close();
	      br.close();

	      //Delete the original file
	      if (!inFile.delete()) {
	        System.out.println("Could not delete file");
	        return;
	      }

	      //Rename the new file to the filename the original file had.
	      if (!tempFile.renameTo(inFile))
	        System.out.println("Could not rename file");

	    }
	    catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    }
	    catch (IOException ex) {
	      ex.printStackTrace();
	    }
	  }
	*/
	public DefaultTableModel getModel() {
		return model;
	}
	
	private int findPosition(int id) {
		int ret = -1;
		for(int i = 0; i<model.getRowCount();i++)
			if(Integer.parseInt(model.getValueAt(i, 0).toString()) == id) {
				ret = i;
			}
		
		return ret;
	}
	
	private int findPosition(String name) {
		int ret = 0;
		for(int i = 0; i<model.getRowCount();i++)
			if(model.getValueAt(i, 1).toString().toLowerCase().equals(name.toLowerCase())) {
				ret = i;
			}
		
		return ret;
	}
	
	private int findPosition(int amount, int i, String type) {
			if(Integer.parseInt(model.getValueAt(i, 2).toString()) == amount) {
				return i;
			}
		return -1;
	}
	
	
	private int findPosition(double price) {
		int ret = 0;
		for(int i = 0; i<model.getRowCount();i++)
			if(Double.parseDouble(model.getValueAt(i, 3).toString().split(" ")[0]) == price) {
				ret = i;
			}
		
		return ret;
	}
	
	
	
	public ItemList getList() {
		return itemList;
	}
	
	private void getTotalWorth() {
		double sum = 0;
		for(int row = 0; row<model.getRowCount();row++) {
			Object value = model.getValueAt(row, 3);
			Object valueAmount = model.getValueAt(row, 2);
			
			double price = Double.parseDouble(value.toString().substring(0, value.toString().length()-1));
			int amount = Integer.parseInt(valueAmount.toString());
			
			sum += price * amount;
		}
		
		JOptionPane.showMessageDialog(frame, "The Full worth of the Inventory is:\n"+sum +" $");
		
	}
	
	private void setIcon(String path) {
		ImageIcon icon = new ImageIcon(path);
        frame.setIconImage(icon.getImage());
	    }

	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public void setFileName(String str) {
		fileName = str;
	}
	
	public void refreshTable() {
	    // 1. Wipe the visual table only
	    model.setRowCount(0);
	    
	    // 2. Grab the current state of your safe, backend memory
	    String[][] dStr = itemList.listToDoubleArray();
	    
	    // 3. Loop through and redraw (starting at 1 to skip the header row!)
	    for (int i = 1; i < dStr.length; i++) {
	        dStr[i][3] += " $";
	        model.addRow(dStr[i]);
	    }
	}
	
}
