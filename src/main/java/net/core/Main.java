package net.core;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Demonstrates Base64 encoding and decoding in Java.
 */
public class Main {

    public static void main(String[] args) {
        String input = "Hello, World!";
        try {
            // Encode the input string
            String encoded = Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
            
            // Decode the Base64 string back to the original bytes
            String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);

            // Print results
            System.out.println("Original: " + input);
            System.out.println("Encoded: " + encoded);
            System.out.println("Decoded: " + decoded);

        } catch (Exception e) {
            System.err.println("Error during Base64 encoding/decoding: " + e.getMessage());
            e.printStackTrace();
        }
    }
}