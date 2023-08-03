package ua.dmitriiev.beautysaloon.lib;

import org.apache.commons.lang3.RandomStringUtils;

public class OrderUtils {

    public static String generateRandomOrderName() {

        return RandomStringUtils.randomAlphanumeric(8).toUpperCase();
    }
}
