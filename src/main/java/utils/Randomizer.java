package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Randomizer {

    public static String getRandomAlphabeticValue(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }


    public static String getRandomPassword() {
        String password = "";
        for (int i = 0; i < 3; i++) {
            password += RandomStringUtils.randomAlphabetic(1).toLowerCase();
            password += RandomStringUtils.randomNumeric(1);
            password += RandomStringUtils.randomAlphabetic(1).toUpperCase();
            password += RandomStringUtils.random(1, "~!@#$%^&*()_+-=[]{};':,./<>?");
        }
        return password;
    }
}
