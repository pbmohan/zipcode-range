package com.abc.app.zipcoderange.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.abc.app.zipcoderange.model.ZipCodeRange;
/*
 * Utility Class to support ZIP code range exclusion application
 */

public class ZipCodeRangeUtils {

	// Regular expression patterns for checking 5-digit values for ZIP code and
	// removing square brackets
	private static final Pattern zipCode5Pattern = Pattern.compile("\\d{5}");
	private static final Pattern sqaureBracketValuePattern = Pattern.compile("^\\[(.*?)\\]");
	private static final String rangeSeparator = ",";

	/**
	 * Optimizes a list of ZipCodeRange objects into the minimum possible ZipCode
	 * ranges.
	 * 
	 * @param ranges The list of ZipCodeRange objects to be processed
	 * @return A List of optimized (ascending) ZipCodeRange objects
	 */
	public static List<ZipCodeRange> optimizeZipCodeRanges(List<ZipCodeRange> zipCodeRanges) {
		Objects.requireNonNull(zipCodeRanges, "The zipcode ranges list is null");

		// Sort ZipCode ranges by lower bound and upper bound in an ascending manner
		zipCodeRanges.sort(Comparator.comparing(ZipCodeRange::getLower).thenComparing(ZipCodeRange::getUpper));

		System.out.println("\n The sorted input zipcode ranges are : \n");

		zipCodeRanges.forEach(zipCodeRange -> System.out.println(zipCodeRange.toString()));
		List<ZipCodeRange> optimzedZipCodeRanges = new ArrayList<ZipCodeRange>();

		// Get the 0th range element's lower and upper values as previous object
		int previousLower = zipCodeRanges.get(0).getLower();
		int previousUpper = zipCodeRanges.get(0).getUpper();

		// Loop through from 1st range element and check if current lower bound value is
		// greater than previous upper and current lower bound is not adjacent previous
		// upper value
		// if condition matches, then add ZipCode range as individual range item to
		// optimized list, else find the maximum of upper value and add them as single
		// item to optimized list
		for (int index = 1; index < zipCodeRanges.size(); index++) {
			ZipCodeRange current = zipCodeRanges.get(index);
			if (current.getLower() > previousUpper && current.getLower() - 1 != previousUpper) {
				optimzedZipCodeRanges.add(setZipCodeRange(previousLower, previousUpper));
				previousLower = current.getLower();
				previousUpper = current.getUpper();
			} else {
				previousUpper = Math.max(current.getUpper(), previousUpper);
			}
		}
		optimzedZipCodeRanges.add(setZipCodeRange(previousLower, previousUpper));
		return optimzedZipCodeRanges;
	}

	/**
	 * Builds a ZipCodeRange object into the minimum possible ZipCode ranges.
	 * 
	 * @param ZipCode Range value (example -> [23432,45434]
	 * @return ZipCodeRange object after validation of the input ZipCode range.
	 * @throws IllegalArgumentException for an Invalid ZipCode range
	 *                                  format,Incorrect values for ZipCode range
	 *                                  values
	 */
	public static ZipCodeRange constructZipCodeRange(String zipCodeRangeValue) {
		ZipCodeRange zipCodeRange = new ZipCodeRange();
		Objects.requireNonNull(zipCodeRangeValue, "ZipCode Range is null");
		// removes square brackets
		Matcher match = sqaureBracketValuePattern.matcher(zipCodeRangeValue);
		if (match.find()) {
			System.out.println(match.group(1));
			zipCodeRangeValue = match.group(1);
		} else {
			throw new IllegalArgumentException("Invalid Zipcode Range format: " + zipCodeRangeValue);
		}
		String[] zipCodeRangeValues = zipCodeRangeValue.split(rangeSeparator);
		if (zipCodeRangeValues.length != 2) {
			throw new IllegalArgumentException("Invalid Zipcode Range format: " + zipCodeRangeValue);
		} else {
			if (!validateZipCodePattern(zipCodeRangeValues[0])) {
				throw new IllegalArgumentException(
						"Invalid Lower Value (must be 5 digit number): " + zipCodeRangeValues[0]);
			}
			if (!validateZipCodePattern(zipCodeRangeValues[1]))

			{
				throw new IllegalArgumentException(
						"Invalid Upper Value (must be 5 digit number): " + zipCodeRangeValues[1]);
			}

			int lowerRangeValue = Integer.valueOf(zipCodeRangeValues[0]);
			int upperRangeValue = Integer.valueOf(zipCodeRangeValues[1]);
			validateZipCodeValues(lowerRangeValue, upperRangeValue);
			// If lower bound value is greater than upper bound, then reverse it
			if (lowerRangeValue > upperRangeValue) {
				System.out.println(
						"INFO: The lower bound zip is greater than upper bound for Zip Range: " + zipCodeRangeValue);
				int temporaryValue = lowerRangeValue;
				lowerRangeValue = upperRangeValue;
				upperRangeValue = temporaryValue;
				System.out.println("INFO: Corrected range for reverse range : " + lowerRangeValue + rangeSeparator
						+ upperRangeValue);

			}
			zipCodeRange.setLower(lowerRangeValue);
			zipCodeRange.setUpper(upperRangeValue);
		}
		return zipCodeRange;
	}

	/*
	 * Checks whether ZIP code is excluded or not from the exclusion list
	 * 
	 * @param List of ZipCode exlusion ranges and ZipCode to be checked if its
	 * excluded or not
	 * 
	 * @return boolean if the given ZipCode is excluded or not
	 */
	public static boolean isExcludedZipcode(List<ZipCodeRange> zipCodeRanges, int zipCode) {
		AtomicBoolean isExcluded = new AtomicBoolean(false);
		zipCodeRanges.forEach(zipCodeRange -> {
			if (zipCode >= zipCodeRange.getLower() && zipCode <= zipCodeRange.getUpper()) {
				isExcluded.set(true);
			}
		});
		return isExcluded.get();
	}

	/*
	 * Populates ZipCodeRange object using params ( low and upper)
	 * 
	 * @param Integer values of Lower bound and Upper bound
	 * 
	 * @return ZipCodeRange object with lower bound and upper bound values populated
	 */
	public static ZipCodeRange setZipCodeRange(int lower, int upper) {
		ZipCodeRange zipCodeRange = new ZipCodeRange();
		zipCodeRange.setLower(lower);
		zipCodeRange.setUpper(upper);
		return zipCodeRange;
	}

	/*
	 * Check to see if the zipCode is valid 5-digit number
	 * 
	 * @param ZipCode needs to be validated bound and Upper bound values
	 * 
	 * @return true/false value if its valid 5-digit number or not
	 */
	public static boolean validateZipCodePattern(String zipCode) {
		return zipCode5Pattern.matcher(zipCode).matches();
	}

	/*
	 * Check to see if the zipCode is between the valid US range of 00000 and 99999
	 * 
	 * @param Integer values of Lower bound and Upper bound
	 * 
	 * @throws IllegalArgumentException if the values are not within standard range
	 */
	public static void validateZipCodeValues(int lower, int upper) {
		if (lower < 0 || lower > 99999) {
			throw new IllegalArgumentException(
					"Error - The zipcode{} " + lower + " is not a valid zipcode (must be between 00000 and 99999)");
		} else if (upper < 0 || upper > 99999) {
			throw new IllegalArgumentException(
					"Error - The zipcode{} " + upper + " is not a valid zipcode (must be between 00000 and 99999)");
		}
	}

}
