package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA256Builder {

	public static String generarHash(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
			StringBuffer hexString = new StringBuffer();

			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
