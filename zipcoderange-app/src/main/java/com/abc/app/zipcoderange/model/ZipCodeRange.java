package com.abc.app.zipcoderange.model;

/**
 * A Class to store ZipCode range values ( both lower and upper bounds)
 */
public class ZipCodeRange {
	private int lower;
	private int upper;

	/*
	 * Constructor accepts lower and upper values of ZipCode range and sets it to
	 * the instance
	 */
	public ZipCodeRange(int lower, int upper) {

		this.lower = lower;
		this.upper = upper;
	}
	/*
	 * Default Constructor
	 */

	public ZipCodeRange() {
		super();
	}

	/*
	 * Gets the lower bound value of ZipCode range
	 */
	public int getLower() {
		return lower;
	}

	/*
	 * Populate the lower bound value of ZipCode range
	 * 
	 * @param The lower bound value of ZipCode Range
	 */
	public void setLower(int lower) {
		this.lower = lower;
	}

	/*
	 * Gets the upper bound value of ZipCode range
	 */
	public int getUpper() {
		return upper;
	}

	/*
	 * Populate the upper bound value of ZipCode range
	 * 
	 * @param The upper bound value of ZipCode Range
	 */
	public void setUpper(int upper) {
		this.upper = upper;
	}

	/*
	 * Retrieves the string representation of an instance
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZipCodeRange {lower bound: " + String.format("%05d", this.lower) + ", upper bound: "
				+ String.format("%05d", this.upper) + "}";
	}
}