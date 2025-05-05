package dungeon_crawler;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
	private String description;
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private HashMap<String, Room> exits = new HashMap<String, Room>();
	
	public Room(String description) {
		this.description = description;
	}
	
	public void addItemToRoom(Item item) {
		itemList.add(item);
	}

	public String getDescription() {
		return description;
	}
	
	public Room getNextRoom(String direction) {
		return exits.get(direction);
	}
	
	public Item removeItem(String name) {
		Item returnItem = null;
		for(int i = 0; i< itemList.size(); i++) {
			if(itemList.get(i).getName().toLowerCase().equals(name)) {
				returnItem = itemList.get(i);
				itemList.remove(i);
			}
		}
		return returnItem;
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void addRoom(String direction ,Room room) {
		exits.put(direction, room);
	}
	public void roomInformation() {
		System.out.println(this.description);
		System.out.println("these items can be found in the room:");
		for(Item item: itemList) {
			System.out.println(item.getName());
		}
		System.out.println("your possible directions are: ");
		for(String i: exits.keySet()) {
			System.out.println(i);
		}
	}
}
