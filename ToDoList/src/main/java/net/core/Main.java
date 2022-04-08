package net.core;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Main {	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] bytes = "Hello, World!".getBytes("UTF-8");
		String encoded = Base64.getEncoder().encodeToString(bytes);
		byte[] decoded = Base64.getDecoder().decode(encoded);
		
		System.out.println(encoded);
		System.out.println(decoded);
	}

}
