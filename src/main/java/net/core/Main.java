package net.core;

import java.util.Base64;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String input = "Hello, World!";
        try {
            String encoded = Base64.getEncoder().encodeToString(input.getBytes("UTF-8"));
            String decoded = new String(Base64.getDecoder().decode(encoded), "UTF-8");

            System.out.println("Original: " + input);
            System.out.println("Encoded: " + encoded);
            System.out.println("Decoded: " + decoded);

            System.out.println("Press Enter to exit...");
            new Scanner(System.in).nextLine(); // Waits for user input before exiting

        } catch (Exception e) {
            System.err.println("Error during Base64 encoding/decoding: " + e.getMessage());
            e.printStackTrace();
        }
    }
}