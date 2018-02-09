package com.segunfamisa.sample.comics.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test hash calculator.
 */
public class HashCalculatorTest {

    @Test
    public void calculateHash() {
        // given a hash calculator
        HashCalculator calculator = new HashCalculator();

        // when we call calculate
        // then verify that the calculated hash of some popular strings is as expected
        // reference: https://en.wikipedia.org/wiki/MD5#MD5_hashes
        assertEquals("9e107d9d372bb6826bd81d3542a419d6",
                calculator.calculate("The quick brown fox jumps over the lazy dog"));
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", calculator.calculate(""));
    }
}
