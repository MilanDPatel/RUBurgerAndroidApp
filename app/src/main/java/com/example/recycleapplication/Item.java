
package com.example.recycleapplication;

public class Item {
	private String name;
	private int imageResourceId;
	private double price;
	private String priceString;

	public Item(String name, int imageResourceId, String priceString) {
		this.name = name;
		this.imageResourceId = imageResourceId;
		this.priceString = priceString;

		// Parse the price string to get a double value
		try {
			this.price = Double.parseDouble(priceString.replace("$", "").trim());
		} catch (NumberFormatException e) {
			this.price = 0; // Default price if parsing fails
		}
	}

	public String getName() {
		return name;
	}

	public int getImageResourceId() {
		return imageResourceId;
	}

	public String getPriceString() {
		return priceString;
	}

	public double getPrice() {
		return price;
	}
}