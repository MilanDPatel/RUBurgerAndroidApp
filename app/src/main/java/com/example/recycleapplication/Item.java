
package com.example.recycleapplication;

/**
 * Represents an item in the application with a name, image, and price.
 * This class is used to store details about the items being offered, such as their name,
 * an associated image resource, and their price.
 * Author: Milan Patel
 */

public class Item {
	private String name;
	private int imageResourceId;
	private double price;
	private String priceString;

	/**
	 * Constructor to initialize an Item object with a name, image resource ID, and price as a string.
	 * The price string is parsed to a double.
	 * @param name The name of the item.
	 * @param imageResourceId The image resource ID representing the item.
	 * @param priceString The price of the item as a string.
	 */
	public Item(String name, int imageResourceId, String priceString) {
		this.name = name;
		this.imageResourceId = imageResourceId;
		this.priceString = priceString;
		try {
			this.price = Double.parseDouble(priceString.replace("$", "").trim());
		} catch (NumberFormatException e) {
			this.price = 0;
		}
	}

	/**
	 * Gets the name of the item.
	 * @return The name of the item.
	 */
	public String getName() {
		return name;
	}

}