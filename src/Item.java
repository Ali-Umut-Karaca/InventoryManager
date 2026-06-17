import java.util.List;

public class Item {
	
	
	/**
	 * @author Ali Umut Karaca / MonteCarlo
	 * 
	 * 
	 * GLOBAL VAIRABLE DECLERATION
	 */
	
	private int id;
	private String name;
	private int amount;
	private double price;
	private double averagePrice;
	private double counter = 0;
	//RETURN INDEX
	private static int index = -1;
	private int ret = 0;
	
	/**
	 * @param id -> ID of the item
	 * @param name -> Name of the item
	 * @param amount -> Stock amount of the item
	 * @param price -> Current Price of the item
	 */
	public Item(int id, String name, int amount, double price) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.price = price;
		this.index++;
		this.ret = index;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getId() {
		return id;
	}
	
	public int getIndex() {
		
		return this.ret;
		
	}
	
	public Item setAveragePrice(double price) {
		
		this.averagePrice = price;
		
		return this;
	}
	
	
	
	public double getAveragePrice() {
		return averagePrice;
	}
	
	
	/**
	 * Custom way of printing the contents of  the item
	 * @param index -> item index to print
	 */
	public void printItem(int index) {
		
		System.out.println("Item["+index+"]"+"{id: "+id + ", name: "+name+", amount:"+amount+", price: "+price+" $"+"}\n");
		
	}
}
