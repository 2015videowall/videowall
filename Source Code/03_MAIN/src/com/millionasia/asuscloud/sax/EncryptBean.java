package com.millionasia.asuscloud.sax;

import java.security.MessageDigest;

public class EncryptBean {

	public static String getMD5(String str) {
		return encrypt(str, "MD5");
	}

	public static String encrypt(String str, String encType) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance(encType);
			md.update(str.getBytes("UTF-8")); //md.update(str.getBytes());
			result = toHexString(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String toHexString(byte[] in) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < in.length; i++) {
			String hex = Integer.toHexString(0xFF & in[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}