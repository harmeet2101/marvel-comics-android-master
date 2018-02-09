package com.segunfamisa.sample.comics.util;


import android.support.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class to calculate MD5 hash of given parameters.
 */
@Singleton
public class HashCalculator {

    @Inject
    public HashCalculator() {

    }

    /**
     * Calculate the has using MD5 digest algorithm.
     *
     * @param args - arguments to concatenate and hash
     * @return - the MD5 hash of the concatenated args
     */
    public String calculate(String... args) {
        String input = concatenateArgs(args);
        byte[] digest = generateDigest(input);
        return toHexString(digest);
    }

    private String concatenateArgs(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString();
    }

    /**
     * Generates MD5 digest from an input string.
     *
     * @param input - input string
     * @return a byte array of the MD5 digest
     */
    @Nullable
    private byte[] generateDigest(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());

            return md.digest();
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return null;
    }

    private String toHexString(byte[] digest) {
        if (digest == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte digestByte : digest) {
            stringBuilder.append(Integer.toString((digestByte & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}
