package com.abc.app.zipcoderange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.abc.app.zipcoderange.model.ZipCodeRange;
import com.abc.app.zipcoderange.utils.ZipCodeRangeUtils;

/**
 * A simple application to optimize ZIP code ranges and check for exclusion
 */

public class ZipCodeRangeApp {

	/**
	 * A runnable method to entry point for this app
	 * 
	 * @param args optional ZIP code values to be checked for exclusion from the ZIP
	 *             code exclusion range {@zipcodes (inputzipcoderangesdata.txt}
	 */
	public static void main(String[] args) {
		List<ZipCodeRange> zipCodeRanges = new ArrayList<ZipCodeRange>();

		// Read the excluded zipcode ranges from a file
		BufferedReader zipCodeRangeInputdata = new BufferedReader(
				new InputStreamReader(ZipCodeRangeApp.class.getResourceAsStream("/inputzipcoderangesdata.txt")));
		try {
			String lineData = zipCodeRangeInputdata.readLine();
			if (null == lineData || lineData.isEmpty()) {
				throw new IllegalArgumentException("No data in input zipcode exclusion range input file");
			}
			while (lineData != null) {
				zipCodeRanges.add(ZipCodeRangeUtils.constructZipCodeRange(lineData));
				lineData = zipCodeRangeInputdata.readLine();
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("No input zipcode ranges data file available", e);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while reading the input zipcode exclusion ranges data ", e);
		}
		Objects.requireNonNull(zipCodeRanges, "The zipcode ranges list is null");
		System.out.println("\n The given zipcode exclusion ranges are : \n");
		zipCodeRanges.forEach(zipCodeRange -> System.out.println(zipCodeRange.toString()));

		List<ZipCodeRange> optimizedZipCdRanges = (!zipCodeRanges.isEmpty() && zipCodeRanges.size()>1)?ZipCodeRangeUtils.optimizeZipCodeRanges(zipCodeRanges):zipCodeRanges;

		System.out.println("\n The optimized zipcode exclusion ranges are : \n");
		optimizedZipCdRanges.forEach(zipCodeRange -> System.out.println(zipCodeRange.toString()));

		/*
		 * If optional ZIP exclusion parameters passed through command-line arguments,
		 * then check whether they are excluded or not from the exclusion list available
		 * in inputzipcoderangesdata.txt
		 */
		if (args != null && args.length > 0) {
			System.out.println("\n The zipcode exclusion check for given zipcode: " + args[0]);
			for (String arg : args) {
				boolean isExcluded = ZipCodeRangeUtils.isExcludedZipcode(optimizedZipCdRanges, Integer.valueOf(arg));
				System.out.println(" Zipcode " + args[0] + " excluded: " + isExcluded);
			}

		}
	}
}