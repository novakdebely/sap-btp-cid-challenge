package org.gpopov.interview.sap.util;

import java.util.Base64;

public class SecretUtil {
	
	public static String encode(String secretValue) {
		return Base64.getEncoder().encodeToString(secretValue.getBytes());
	}
	
	public static String decode(String encodedSecretValue) {
		byte[] secretByte = Base64.getDecoder().decode(encodedSecretValue);

		return new String(secretByte);
	}

}
