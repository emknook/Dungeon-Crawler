package dungeon_crawler;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	// instance variables.
	private Player player;
	private Room startRoom;
	boolean keepPlaying = true;

	// initialise rooms, items and player and add run.
	public Game() {
		startRoom = new Room("It's covered in rubble, broken pottery and small bones.\r\n"
				+ "Your torch allows you to see broken arrows, rusty swords and skeletal remains, frayed and ravaged by time itself.");
		Room room2 = new Room("It's covered in rat droppings, ash and remains.\r\n"
				+ "Your torch allows you to see remnants of a small camp, aged and absorbed by time itself.");
		Room room3 = new Room("It's covered in moss, roots and dead insects.\r\n"
				+ "Your torch allows you to see locked chests, vats, crates and pieces of broken wood, busted and claimed by time itself.");
		Room room4 = new Room("It's covered in bat droppings and roots.\r\n"
				+ "Your torch allows you to see what seems like some form of a sacrificial chamber, pillaged and spoiled by time itself.");
		Room room5 = new Room("It's covered in crawling insects, rat droppings and dead insects.\r\n"
				+ "Your torch allows you to see rows of tombs and several statues, long lost and drained by time itself.");
		Room room6 = new Room("It's covered in dead insects, crawling insects and dirt.\r\n"
				+ "Your torch allows you to see an overgrown underground garden, destroyed and devoured by time itself.");
		Room room7 = new Room("It's covered in broken stone, roots and remains.\r\n"
				+ "Your torch allows you to see rows of statues, decayed and absorbed by time itself.");
		Room room8 = new Room("It's covered in cobwebs, dirt and broken stone.\r\n"
				+ "Your torch allows you to see carved out openings filled with pottery, long lost and taken by time itself.");
		Room room9 = new Room("It's covered in crawling insects, dead vermin and dirt.\r\n"
				+ "Your torch allows you to see remnants of a small camp, wasted and eaten by time itself.");
		Room room10 = new Room("This room seems different, it feels unnaturally bright.\r\n"
				+ "It seems more quiet, and when you look up you see what must be the item you came looking for!");

		startRoom.addRoom("north", room2);

		// add key to room.
		room2.addRoom("north", room4);
		room2.addRoom("west", room3);
		room2.addRoom("east", room7);
		room2.addRoom("south", startRoom);

		room3.addRoom("east", room2);

		room4.addRoom("west", room5);
		room4.addRoom("south", room2);

		room5.addRoom("east", room4);

		room6.addRoom("south", room7);

		room7.addRoom("west", room2);
		room7.addRoom("east", room8);

		room8.addRoom("west", room7);
		room8.addRoom("east", room10);
		room8.addRoom("south", room9);

		room9.addRoom("north", room8);

		room10.addRoom("west", room8);

		Item item1 = new Item("Torch", "lights up the room");
		Item item2 = new Item("HP potion", "added 2 HP points to health");
		Item item3 = new Item("Strange potion",
				"You suddenly feel stronger after drinking this potion, added 3 attack points");
		Item item4 = new Item("HP potion", "added 2 HP points to health");
		Item item5 = new Item("Attack potion", "added 3 attack points");
		Item item6 = new Item("Gem", "you find yourself back at the start");

		// add items to room.
		startRoom.addItemToRoom(item1);
		room5.addItemToRoom(item2);
		room6.addItemToRoom(item3);
		room7.addItemToRoom(item4);
		room9.addItemToRoom(item5);
		room10.addItemToRoom(item6);

		// add player to first room.
		player = new Player();
		player.setCurrentRoom(startRoom);
		run();
	}

	public Player getPlayer() {
		return player;
	}

	// TODO description of run function
	private void run() {
		try (Scanner playerStart = new Scanner(System.in)) {
			System.out.println("You have arrived at the entrance of the dungeon.");
			System.out.println("Would you like to proceed? (yes/no)");
			String playerInput = playerStart.nextLine();
			// handleCommand(playerInput);
			if (playerInput.equals("no")) {
				keepPlaying = false;
				System.out.println("you exited the dungeon");
			} else {
				startRoom.roomInformation();
				while (keepPlaying) {
					String s = playerStart.nextLine().toLowerCase();
					handleCommand(s);
				}
			}
		}
	}

	// split user input, get command and run command.
	private void handleCommand(String userInput) {
		String[] input = userInput.split(" ");

		switch (input[0]) {
		case "go":
			handleGoCommand(input[1]);
			break;
		case "get":
			handleGetCommand(input[1]);
			break;
		case "drop":
			handleDropCommand(input[1]);
			break;
		case "use":
			handleUseCommand(input[1]);
			break;
		case "pack":
			handlePackCommand();
			break;
		case "quit":
			handleQuitCommand();
			break;
		case "help":
			handleHelpCommand();
			break;
		case "look":
			handleLookCommand();
			break;
		}
	}

	// check if player has item in inventory, if not check if item is in the current
	// room.
	// otherwise output player is trying to use a item thats not present.
	private void handleUseCommand(String itemName) {
		Room currentRoom = player.getCurrentRoom();
		Item useItem = null;
		ArrayList<Item> inventory = player.getInventory();
		ArrayList<Item> roomItems = currentRoom.getItemList();

		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getName().toLowerCase().equals(itemName)) {
				useItem = inventory.get(i);
				inventory.remove(i);
			}
		}
		if (useItem == null) {
			for (int i = 0; i < roomItems.size(); i++) {
				if (roomItems.get(i).getName().toLowerCase().equals(itemName)) {
					useItem = roomItems.get(i);
					roomItems.remove(i);
				}
			}
			if (useItem == null) {
				System.out.println("you are trying to use an item thats not there.");
			}
		}
		if (useItem != null) {
			handleItemEffect(useItem);
			System.out.println(useItem.getUsageText());
		}
	}

	// give player effect of used item.
	private void handleItemEffect(Item useItem) {
		switch (useItem.getName()) {
		case "HP potion":
			player.setHealthPoints(player.getHealthPoints() + 2);
			break;
		case "Strange potion":
			player.setAttackPoints(player.getAttackPoints() - 1);
			break;
		case "Attack potion":
			player.setAttackPoints(player.getAttackPoints() + 3);
			break;
		case "Gem":
			player.setCurrentRoom(startRoom);
			break;
		}
	}

	// drop the item in current room.
	private void handleDropCommand(String itemName) {
		Room currentRoom = player.getCurrentRoom();
		Item dropItem = player.dropItem(itemName);
		if (dropItem == null) {
			System.out.println("item not found");
		} else {
			currentRoom.addItemToRoom(dropItem);
			System.out.println("dropped item" + itemName);
		}
	}

	// get item thats present in current room and add it to the inventory of the
	// player.
	// remove the item from current room.
	private void handleGetCommand(String itemName) {
		Room currentRoom = player.getCurrentRoom();
		Item hasItem = currentRoom.removeItem(itemName);
		if (hasItem == null) {
			System.out.println("item not found");
		} else {
			player.addItemToInventory(hasItem);
		}
	}

	// move the player to the next room when given direction.
	private void handleGoCommand(String direction) {
		Room currentRoom = player.getCurrentRoom();
		Room nextRoom = currentRoom.getNextRoom(direction);
		if (nextRoom == null) {
			System.out.println("ouch! you walked into the wall");
		} else {
			System.out.println("You have reached the next room.");
			player.setCurrentRoom(nextRoom);
			nextRoom.roomInformation();
		}
	}

	private void handlePackCommand() {
		player.printInventory();
	}

	private void handleQuitCommand() {

	}

	private void handleHelpCommand() {

	}

	private void handleLookCommand() {

	}

	/**
	 * @param command
	 *
	 *                Check if the command can take us to another room. If so: do
	 *                it! Let the caller know if we actually travelled.
	 */
	private boolean checkRoomTravel(String direction) {
		return false;
		// Get the currentRoom from the player and check if this room
		// has an exit in the direction of command. (East, south, north, west.)
		// If there is an exit in that direction, ask the currentRoom to get that
		// that room.
		// If there is no exit in that direction, tell the player.
		// If travel was successful, return true. If not, return false.
	}
}
