package dungeon_crawler;

public class Item {
	private String name, usageText;
	
	public Item(String name, String usageText) {
		this.name = name;
		this.usageText = usageText;
	}

	public String getName() {
		return name;
	}
	
	public String getUsageText() {
		return usageText;
	}
}
