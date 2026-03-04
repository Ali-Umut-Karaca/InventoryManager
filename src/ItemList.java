import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ItemList {
	
   /**
	 * @author Ali Umut Karaca / MonteCarlo
	 * 
	 * -This class is a specific kind of ArrayList structure.
	 * Made specially for containing and manipulating @Item type Objects.
	 * 
	 * -The duplicate methods inside of this file is the result of
	 * me originally writing this file for students to practice their Lambda
	 * and Stream usage skills. I had them implement the same methods
	 * In both ways, resulting in duplicate methods. Will remove them in future.
	 * 
	 */
	
	List <Item> list = new ArrayList();
	private int counter=0;
	
	public ItemList() {}
	
	/**
	 * Adding items to the list by entering an @Item object as parameter.
	 * @param item -> An @Item Object.
	 */
	public void add(Item item) {
		list.add(item);
	}
	/**
	 * Instead of an @Item Object being passed, We pass a String array as the attributes <of the Item.
	 * @param itemStr -> String array of @Item attributes.
	 */
	public void add(String [] itemStr) {
		Item item = new Item(
				Integer.parseInt(itemStr[0]),
				itemStr[1],
				Integer.parseInt(itemStr[2]),
				Double.parseDouble(itemStr[3]));
		list.add(item);
		
	}
	
	public Item get(int index) {
		return list.get(index);
	}

	public void printList() {
		
		list.forEach(i -> i.printItem(i.getIndex()));
		
	}
	
	public void printList(List <Item> ls) {
		
		ls.forEach(i -> i.printItem(i.getIndex()));
		
	}
	
	public double getAverage(){
		return list.stream().mapToDouble(i -> i.getPrice()).average().orElse(0);
	}
	
	public double getAverage(String lambda) {
		double cnt = 0;//i -> increaseCounter(i.getPrice())
		list.forEach(i-> counter+=i.getPrice());
		
		cnt = (double) counter/list.size();
		
		counter = 0;
		
		return cnt;
	}
	
	public ItemList findByID(int id) {
		ItemList ls = new ItemList();
		ls.list = list.stream().filter(i -> i.getId() == id).toList();
		return ls;
	}
	
	public ItemList findByName(String name) {
		ItemList ls = new ItemList();
		ls.list = list.stream().filter(i -> i.getName().toLowerCase().equals(name.toLowerCase())).toList();
		return ls;
	}
	
	public ItemList findByAmount(int amount) {
		ItemList ls = new ItemList();
		ls.list = list.stream().filter(i -> i.getAmount() == amount).toList();
		return ls;
	}
	
	public ItemList findByPrice(double price) {
		ItemList ls = new ItemList();
		ls.list = list.stream().filter(i -> i.getPrice() == price).toList();
		return ls;
	}
	
	
	public ItemList idOrder() {
		ItemList li = new ItemList();
		
		li.list = list.stream().sorted(Comparator.comparingInt(Item::getId)).toList();
		
		return li;
	}
	
	public ItemList idOrder(String lambda){
		List <Item> ls = new ArrayList<>(this.list);
		
		ls.sort((a,b)-> Integer.compare(a.getId(), b.getId()));
		
		ItemList result = new ItemList();
		
		result.list = ls;
		
		return result;
	} 	
	
	public ItemList alphabeticOrder(){
		ItemList li = new ItemList();
		li.list = list.stream().sorted(Comparator.comparing(Item::getName)).toList();
		return li;
	}
	
	public ItemList alphabeticOrder(String lambda){
		List <Item> li = new ArrayList(this.list);
		
		ItemList ls = new ItemList();
		
		li.sort((a,b) -> a.getName().compareTo(b.getName()));	
		
		ls.list = li;
		
		 return ls;
	}
	
	
	public ItemList priceOrder(){
		ItemList lı = new ItemList();
		lı.list = list.stream().sorted(Comparator.comparingDouble(Item::getPrice)).toList();
		return lı;
	}

	
	public ItemList priceOrder(String lambda){
		List <Item> li = new ArrayList<> (this.list);
		li.sort((a,b) -> Double.compare(a.getPrice(), b.getPrice()));
		
		ItemList result = new ItemList();
		
		result.list = li;
		
		return result;
	}
	
	
	public ItemList amountOrder(){
		ItemList list2 = new ItemList();
		list2.list = list.stream().sorted(Comparator.comparingInt(Item::getAmount)).toList();
		return list2;
		
	}
	
	public ItemList filterItemsByPrice(double price) {
		ItemList list2 = new ItemList();
		
		list2.list = list.stream().filter(i -> i.getPrice()<price).toList();
		
		return list2;
		
		
	}
	
	public ItemList filterItemsByPrice(double price, int k) {
		ItemList list2 = new ItemList();
		
		list2.list = list.stream().filter(i -> i.getPrice()>price).toList();
		
		return list2;
		
		
	}
	
	
	public ItemList filterItemsById(double price) {
		ItemList list2 = new ItemList();
		
		
		return list2;
		
		
	}

	public ItemList filterItemsByName(double price) {
		ItemList list2 = new ItemList();
		
		
		return list2;
		
		
	}

	public ItemList filterItemsByAmount(double price) {
		ItemList list2 = new ItemList();
		
		return list2;
		
		
	}

	
	public ItemList amountOrder(String lambda){
		return null;
	}
	
	public void updateItemInList(Item item) {
		list.removeIf(a -> a.getId() == item.getId());
		list.add(item);
	}

	public ItemList updateItemInList(String [] itemStr) {
		ItemList itmLst = new ItemList();
		itmLst.list = new ArrayList<>(this.list);
		
		Item item = new Item(
				Integer.parseInt(itemStr[0]),
				itemStr[1],
				Integer.parseInt(itemStr[2]),
				Double.parseDouble(itemStr[3]));
		
		itmLst.list.removeIf(a -> a.getId() == item.getId());
		
		itmLst.list.add(item);
		
		return itmLst;
		
	}
	
	public String [][] listToDoubleArray() {
		String [][] str = new String[list.size()+1][5];
		str[0][0] = "ID";
		str[0][1] = "NAME";
		str[0][2] = "AMOUNT";
		str[0][3] = "PRICE";
		str[0][4] = "AVERAGE_PRICE";
		for(int i = 0; i<list.size(); i++) {
				
				str[i+1][0] = Integer.toString(list.get(i).getId());
				str[i+1][1] = list.get(i).getName();
				str[i+1][2] = Integer.toString(list.get(i).getAmount());
				str[i+1][3] = Double.toString(list.get(i).getPrice());
		}
		
		System.out.println(Arrays.deepToString(str));
		
		return str;
	}
	
	private double increaseCounter(double i) {
		return counter += i;
	}
	
	
}
