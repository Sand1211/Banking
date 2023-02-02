package com.bank.application.util;

import java.security.SecureRandom;

public class PasswordUtil {

		private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		private static final String NUM = "0123456789";
		private static final String SPL_CHARS = "!@#$%^&*";
		private static final String COMB_CHARS = ALPHA_CAPS + ALPHA + NUM + SPL_CHARS;

		public static String generatePswd(int length) {

			char[] password = new char[length];
			SecureRandom random = new SecureRandom();
			if (length < 5) {
				throw new IllegalArgumentException("Length should be atleast 5 Characters");
			}

			password[0] = ALPHA.charAt(random.nextInt(ALPHA.length()));
			password[1] = ALPHA_CAPS.charAt(random.nextInt(ALPHA_CAPS.length()));
			password[2] = SPL_CHARS.charAt(random.nextInt(SPL_CHARS.length()));
			password[3] = NUM.charAt(random.nextInt(NUM.length()));

			for (int i = 4; i < length; i++) {
				password[i] = COMB_CHARS.charAt(random.nextInt(COMB_CHARS.length()));
			}
			return new String(password);
		}
	}

