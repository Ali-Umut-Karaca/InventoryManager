import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Locale;
public class SQLHandler {
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo
	 */
	
	/**
	 * GLOBAL VARIABLE DECLERATIONs
	 */
	//This url is not optimized to work in different environments. 
	//Please edit the url to your suiting database file address.
	static String url = "jdbc:sqlite:C:\\Users\\hp\\eclipse-workspace\\InventoryApplication\\Items.db";
	static Connection cn;
	static Statement st;
	public static void main(String [] args) throws Exception{
		//printTable();
	}
	
	/**
	 * Print the whole SQL table in a formatted way in which,
	 * If the text goes past 5 characters, it turns into ... to not disturb structure
	 * @throws SQLException
	 */
	public void printTable() throws SQLException {
		cn = DriverManager.getConnection(url);
		st = cn.createStatement();
		ResultSet rs = st.executeQuery("Select * from Items");
		ResultSetMetaData metaRs = rs.getMetaData();
		for (int i = 1; i <= metaRs.getColumnCount(); i++) {
		    System.out.print(metaRs.getColumnName(i) + "\t");
		}
		System.out.println();
		System.out.println("--\t----\t------\t-----");
		
		// Print rows
		while (rs.next()) {
		    for (int i = 1; i <= metaRs.getColumnCount(); i++) {
		    	String prt = rs.getString(i);
		    	if(prt.length()>5 && i==2) {
		    		prt = prt.substring(0, 3) + "..";;
		    	}
		        System.out.print(prt + "\t");
		    }
		    System.out.println();
		}
		st.close();
	}
	
	/**
	 * Write rows into the database.
	 * @param str -> The rows to write in the String array type
	 */
	public void writeRow(String str []) {
		try {
			cn = DriverManager.getConnection(url);
			Statement st = cn.createStatement();
			st.setQueryTimeout(30);
			Arrays.toString(str);
			String format = "Insert into Items values (30,'kest',20,30.0)";
			String frmt = String.format(Locale.US,
										"INSERT into Items VALUES (%d, '%s', %d, %f);",
										Integer.parseInt(str[0]),
										str[1],
										Integer.parseInt(str[2]),
										Double.parseDouble(str[3])
										);
			System.out.println(frmt);
			st.executeUpdate(frmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param id -> Enter id (Primary Key) of item to remove from the database.
	 * @return -> If succesful, return true. Else, return false.
	 */
	public boolean deleteRow(int id) {
		try {
			cn = DriverManager.getConnection(url);
			Statement st = cn.createStatement();
			st.executeUpdate("DELETE from Items where id="+id+";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("The Program Exited with Error code DLT01");
			return false;
		}
		return true;
		
	}
	
	/**
	 * @IMPORTANT @README
	 * This method is only ran ONCE in the initialization phase.
	 * What it does is, It takes the uninitialized database, 
	 * parses in the data from the .csv or .txt file the user provides.
	 * By doing this action, initializes the DB with the data in the files.
	 * @param li
	 */
	public void loadUpdatedData(ItemList li) { // Only run once if in the INITIALIZATION phase.
		String cpy [][] = li.listToDoubleArray();
		for(int i = 1; i<=li.list.size(); i++) {
				writeRow(cpy[i]);
			
		}
		
}
	/**
	 * Helper method for getting Connection
	 * @return -> Returns @Connection after establishing it.
	 */
	public static Connection getConnection(){
		try(Connection dm = DriverManager.getConnection(url)){
			return dm;
		}
		catch (Exception e) {
			System.out.println("There was a problem connecting to the database. Please contact your sysadmin");
		return null;
		}
	}
	
}
