package com.gymmanagement.util;

public class ScreenUtil {
    public static void clearScreen() {
        // ANSI escape code to clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
} 