package dungeon_crawler;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Room currentRoom;
	private int healthPoints;
	private int attackPoints;
	
	public Player() {
		this.healthPoints = 10;
		this.attackPoints = 5;
	}
	
	//drop the item in the current room
	public Item dropItem(String name) {
		Item dropItem = null;
		for(int i = 0; i< inventory.size(); i++) {
			if(inventory.get(i).getName().equals(name)) {
				dropItem = inventory.get(i);
				inventory.remove(i);
			}
		}
		return dropItem;
	}
	
	//use the item and remove it from the inventory
	public void useItem(String name) {
		Item useItem = null;
		for(int i = 0; i< inventory.size(); i++) {
			if(inventory.get(i).getName().equals(name)) {
				useItem = inventory.get(i);
				System.out.println(useItem.getUsageText());
				inventory.remove(i);
			}
		}
		if(useItem == null) {
			System.out.println("item not found");	
		}
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	// get items from inventory
	public void printInventory() {
		for(Item item: inventory) {
			System.out.println(item.getName());
		}
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void addItemToInventory(Item item) {
		inventory.add(item);
	}

}
