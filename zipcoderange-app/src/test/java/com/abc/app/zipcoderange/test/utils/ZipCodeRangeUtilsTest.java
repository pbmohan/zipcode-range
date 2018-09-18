package com.abc.app.zipcoderange.test.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.abc.app.zipcoderange.model.ZipCodeRange;
import com.abc.app.zipcoderange.utils.ZipCodeRangeUtils;

/*
 * Simple JUnit class to execute all the test cases for ZipCode exclusion range optimization application
 */

public class ZipCodeRangeUtilsTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public final void testOptimizeZipCodeRangesWithNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("The zipcode ranges list is null");
		List<ZipCodeRange> zipCodeRanges = ZipCodeRangeUtils.optimizeZipCodeRanges(null);
		assertNull(zipCodeRanges);
	}

	@Test
	public final void testOptimizeZipCodeRangesWithNoOverlapWtihCorrectOrder() {
		List<ZipCodeRange> expectedZipCodeRanges = new ArrayList<ZipCodeRange>();
		ZipCodeRange zipCodeRange1 = new ZipCodeRange(23433, 34543);
		ZipCodeRange zipCodeRange2 = new ZipCodeRange(12343, 12350);
		ZipCodeRange zipCodeRange3 = new ZipCodeRange(11042, 11230);
		expectedZipCodeRanges.add(zipCodeRange3);
		expectedZipCodeRanges.add(zipCodeRange2);
		expectedZipCodeRanges.add(zipCodeRange1);
		List<ZipCodeRange> optimizedZipCodeRanges = ZipCodeRangeUtils
				.optimizeZipCodeRanges(Arrays.asList(zipCodeRange1, zipCodeRange2, zipCodeRange3));
		assertNotNull(optimizedZipCodeRanges);
		assertEquals(3, optimizedZipCodeRanges.size());
		assertArrayEquals(new int[] { 11042, 11230 },
				new int[] { optimizedZipCodeRanges.get(0).getLower(), optimizedZipCodeRanges.get(0).getUpper() });
		assertArrayEquals(new int[] { 12343, 12350 },
				new int[] { optimizedZipCodeRanges.get(1).getLower(), optimizedZipCodeRanges.get(1).getUpper() });
		assertArrayEquals(new int[] { 23433, 34543 },
				new int[] { optimizedZipCodeRanges.get(2).getLower(), optimizedZipCodeRanges.get(2).getUpper() });
	}

	@Test
	public final void testOptimizeZipCodeRangesWithOverlap() {
		List<ZipCodeRange> expectedZipCodeRanges = new ArrayList<ZipCodeRange>();
		ZipCodeRange zipCodeRange1 = new ZipCodeRange(23433, 34543);
		ZipCodeRange zipCodeRange2 = new ZipCodeRange(12343, 12450);
		ZipCodeRange zipCodeRange3 = new ZipCodeRange(11042, 12350);
		expectedZipCodeRanges.add(new ZipCodeRange(11042, 12450));
		expectedZipCodeRanges.add(zipCodeRange1);
		List<ZipCodeRange> optimizedZipCodeRanges = ZipCodeRangeUtils
				.optimizeZipCodeRanges(Arrays.asList(zipCodeRange1, zipCodeRange2, zipCodeRange3));
		assertNotNull(optimizedZipCodeRanges);
		assertEquals(2, optimizedZipCodeRanges.size());
		assertArrayEquals(new int[] { 11042, 12450 },
				new int[] { optimizedZipCodeRanges.get(0).getLower(), optimizedZipCodeRanges.get(0).getUpper() });
		assertArrayEquals(new int[] { 23433, 34543 },
				new int[] { optimizedZipCodeRanges.get(1).getLower(), optimizedZipCodeRanges.get(1).getUpper() });
	}

	@Test
	public final void testOptimizeZipCodeRangesOverlapBoth() {
		ZipCodeRange zipCodeRange1 = new ZipCodeRange(23433, 34543);
		ZipCodeRange zipCodeRange2 = new ZipCodeRange(12343, 12350);
		ZipCodeRange zipCodeRange3 = new ZipCodeRange(11042, 12450);
		List<ZipCodeRange> optimizedZipCodeRanges = ZipCodeRangeUtils
				.optimizeZipCodeRanges(Arrays.asList(zipCodeRange1, zipCodeRange2, zipCodeRange3));
		assertNotNull(optimizedZipCodeRanges);
		assertEquals(2, optimizedZipCodeRanges.size());
		assertArrayEquals(new int[] { 11042, 12450 },
				new int[] { optimizedZipCodeRanges.get(0).getLower(), optimizedZipCodeRanges.get(0).getUpper() });
		assertArrayEquals(new int[] { 23433, 34543 },
				new int[] { optimizedZipCodeRanges.get(1).getLower(), optimizedZipCodeRanges.get(1).getUpper() });
	}

	public final void testOptimizeZipCodeRangesAdjacentLower() {
		ZipCodeRange zipCodeRange1 = new ZipCodeRange(23433, 34543);
		ZipCodeRange zipCodeRange2 = new ZipCodeRange(12351, 12450);
		ZipCodeRange zipCodeRange3 = new ZipCodeRange(11042, 12350);
		List<ZipCodeRange> optimizedZipCodeRanges = ZipCodeRangeUtils
				.optimizeZipCodeRanges(Arrays.asList(zipCodeRange1, zipCodeRange2, zipCodeRange3));
		assertNotNull(optimizedZipCodeRanges);
		assertEquals(2, optimizedZipCodeRanges.size());
		assertArrayEquals(new int[] { 11042, 12450 },
				new int[] { optimizedZipCodeRanges.get(0).getLower(), optimizedZipCodeRanges.get(0).getUpper() });
		assertArrayEquals(new int[] { 23433, 34543 },
				new int[] { optimizedZipCodeRanges.get(1).getLower(), optimizedZipCodeRanges.get(1).getUpper() });
	}

	public final void testOptimizeZipCodeRangesUpperGreaterThanLower() {
		ZipCodeRange zipCodeRange1 = new ZipCodeRange(23433, 34542);
		ZipCodeRange zipCodeRange2 = new ZipCodeRange(40234, 34543);
		ZipCodeRange zipCodeRange3 = new ZipCodeRange(11042, 12350);
		List<ZipCodeRange> optimizedZipCodeRanges = ZipCodeRangeUtils
				.optimizeZipCodeRanges(Arrays.asList(zipCodeRange1, zipCodeRange2, zipCodeRange3));
		assertNotNull(optimizedZipCodeRanges);
		assertEquals(2, optimizedZipCodeRanges.size());
		assertArrayEquals(new int[] { 11042, 12350 },
				new int[] { optimizedZipCodeRanges.get(0).getLower(), optimizedZipCodeRanges.get(0).getUpper() });
		assertArrayEquals(new int[] { 23433, 40234 },
				new int[] { optimizedZipCodeRanges.get(0).getLower(), optimizedZipCodeRanges.get(0).getUpper() });
	}

	@Test
	public final void testIsExcludedZipcode() {
		ZipCodeRange zipCodeRange1 = new ZipCodeRange(23433, 34542);
		ZipCodeRange zipCodeRange2 = new ZipCodeRange(34543, 40234);
		ZipCodeRange zipCodeRange3 = new ZipCodeRange(11042, 12350);
		List<ZipCodeRange> optimizedZipCodeRanges = ZipCodeRangeUtils
				.optimizeZipCodeRanges(Arrays.asList(zipCodeRange1, zipCodeRange2, zipCodeRange3));
		assertTrue(ZipCodeRangeUtils.isExcludedZipcode(optimizedZipCodeRanges, 39243));
		assertFalse(ZipCodeRangeUtils.isExcludedZipcode(optimizedZipCodeRanges, 95630));

	}

	@Test
	public final void testConstructZipCodeRangeWithNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("ZipCode Range is null");
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange(null);
		assertNull(actualZipCodeRange);
	}

	@Test
	public final void testConstructZipCodeRangeValid() {
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange("[12343,12343]");
		assertNotNull(actualZipCodeRange);
		assertArrayEquals(new int[] {12343,12343},new int[] {actualZipCodeRange.getLower(),actualZipCodeRange.getUpper()});
	}

	@Test
	public final void testConstructZipcodeRangeInvalidLowerValue() {
		String expectedMsg = "Invalid Lower Value (must be 5 digit number): 12A43";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(expectedMsg.toString());
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange("[12A43,12343]");
		assertNull(actualZipCodeRange);
	}

	@Test
	public final void testConstructZipcodeRangeInvalidUpperValue() {
		String expectedMsg = "Invalid Upper Value (must be 5 digit number): 1$343";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(expectedMsg.toString());
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange("[12343,1$343]");
		assertNull(actualZipCodeRange);
	}

	@Test
	public final void testConstructZipcodeRangeIncorrectRangeFormatWithThreeOrMoreRanges() {
		String incorrectZipCodeRangeValue = "[12A43,12343,23413]";
		String expectedMsg = "Invalid Zipcode Range format: ";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage((expectedMsg + "12A43,12343,23413").toString());
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange(incorrectZipCodeRangeValue);
		assertNull(actualZipCodeRange);
	}

	@Test
	public final void testConstructZipcodeRangeIncorrectRangeFormatWithOtherChars() {
		String incorrectZipCodeRangeValue = "#12A43,12343}";
		String expectedMsg = "Invalid Zipcode Range format: ";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage((expectedMsg + incorrectZipCodeRangeValue).toString());
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange(incorrectZipCodeRangeValue);
		assertNull(actualZipCodeRange);
	}

	@Test
	public final void testConstructZipcodeRangeIncorrectRangeFormatWithWrongSeprator() {
		String incorrectZipCodeRangeValue = "[12343-23413]";
		String expectedMsg = "Invalid Zipcode Range format: ";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage((expectedMsg + "12343-23413").toString());
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange(incorrectZipCodeRangeValue);
		assertNull(actualZipCodeRange);
	}

	@Test
	public final void testConstructZipCodeRangeUpperGreaterThanLower() {
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.constructZipCodeRange("[98373,29358]");
		assertNotNull(actualZipCodeRange);
		assertArrayEquals(new int[] { 29358, 98373 },
				new int[] { actualZipCodeRange.getLower(), actualZipCodeRange.getUpper() });
	}

	@Test
	public final void testSetZipCodeRangeValid() {
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.setZipCodeRange(22343, 22434);
		assertNotNull(actualZipCodeRange);
		assertArrayEquals(new int[] { 22343, 22434 },
				new int[] { actualZipCodeRange.getLower(), actualZipCodeRange.getUpper() });
	}

	@Test
	public final void testSetZipCodeRangeZeroValue() {
		ZipCodeRange actualZipCodeRange = ZipCodeRangeUtils.setZipCodeRange(22343, 0);
		assertNotNull(actualZipCodeRange);
		assertArrayEquals(new int[] { 22343, 0 },
				new int[] { actualZipCodeRange.getLower(), actualZipCodeRange.getUpper() });
	}

	@Test
	public final void validateZipCodePatternValid() {
		boolean valid = false;
		valid = ZipCodeRangeUtils.validateZipCodePattern("95630");
		assertTrue(valid);
	}

	@Test
	public final void validateZipCodePatternInvalid() {
		boolean inValid = false;
		inValid = ZipCodeRangeUtils.validateZipCodePattern("ZA343");
		assertFalse(inValid);
	}

	@Test
	public final void testValidateZipCodeValuesLower() {
		int lowerZipCode = -23455;
		int upperZipcode = 95673;
		String expectedMsg = "Error - The zipcode{} " + lowerZipCode
				+ " is not a valid zipcode (must be between 00000 and 99999)";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(expectedMsg.toString());
		ZipCodeRangeUtils.validateZipCodeValues(lowerZipCode, upperZipcode);
	}

	@Test
	public final void testValidateZipCodeValuesUpper() {
		int lowerZipCode = 23455;
		int upperZipcode = 100000;
		String expectedMsg = "Error - The zipcode{} " + upperZipcode
				+ " is not a valid zipcode (must be between 00000 and 99999)";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(expectedMsg.toString());
		ZipCodeRangeUtils.validateZipCodeValues(lowerZipCode, upperZipcode);
	}
}
