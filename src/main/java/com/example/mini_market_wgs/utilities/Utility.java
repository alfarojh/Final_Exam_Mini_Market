package com.example.mini_market_wgs.utilities;

import java.util.ResourceBundle;

public class Utility {

    // Fungsi untuk mengambil nilai dari properties berdasarkan key.
    public static String message(String key) {
        return ResourceBundle.getBundle("messages").getString(key);
    }

    // Fungsi untuk mengambil nilai dari properties berdasarkan key beserta param.
    public static String message(String key, String param) {
        String keyMessage = ResourceBundle.getBundle("messages").getString(key);
        return keyMessage.replace("${param}", String.valueOf(param));
    }

    // Fungsi untuk validasi input tidak berupa huruf dan bilangan bulat.
    public static boolean isNotAlphanumeric(String input) {
        return (input == null) || !input.matches("[a-zA-Z0-9\\s]+");
    }

    // Fungsi untuk validasi nomor telepon.
    public static boolean isPhoneNumberNotValid(String phoneNumber) {
        return phoneNumber == null || !phoneNumber.matches("\\d{8,}");
    }
}
