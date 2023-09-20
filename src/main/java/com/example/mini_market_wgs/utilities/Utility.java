package com.example.mini_market_wgs.utilities;

import java.util.ResourceBundle;

public class Utility {

    public static String message(String key, String param) {
        String keyMessage = ResourceBundle.getBundle("messages").getString(key);
        return keyMessage.replace("${param}", String.valueOf(param));
    }

    public static String message(String key) {
        return ResourceBundle.getBundle("messages").getString(key);
    }

    /**
     * Memeriksa apakah sebuah nama valid.
     * Nama yang valid hanya mengandung huruf (a-z, A-Z), angka (0-9), dan spasi.
     *
     * @param input Nama yang akan diperiksa.
     * @return      true jika nama valid, false jika tidak valid.
     */
    public static boolean isNotAlphanumeric(String input) {
        return (input == null) || !input.matches("[a-zA-Z0-9\\s]+");
    }

    public static boolean isPhoneNumberNotValid(String phoneNumber) {
        return phoneNumber == null || !phoneNumber.matches("\\d{8,}");
    }
}
