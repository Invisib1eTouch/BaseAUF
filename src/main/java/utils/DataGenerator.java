package utils;

import models.LoginViewModel;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static String getRandomAlphabeticValue(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }


    public static String getRandomPassword() {
        String password = "";
        for (int i = 0; i < 3; i++) {
            password += RandomStringUtils.randomAlphabetic(1).toLowerCase();
            password += RandomStringUtils.randomNumeric(1);
            password += RandomStringUtils.randomAlphabetic(1).toUpperCase();
            password += RandomStringUtils.random(1, "!@#$%^&*");
        }
        return password;
    }

    public static List<LoginViewModel> generateUserCredentials(int numberOfCredentials) {
        List<LoginViewModel> userCredentials = new ArrayList<>();
        for (int i = 0; i < numberOfCredentials; i++) {
            LoginViewModel model = new LoginViewModel.Builder()
                    .addUsername(getRandomAlphabeticValue(10))
                    .addPassword(getRandomPassword())
                    .build();

            userCredentials.add(model);
        }
        return userCredentials;
    }
}