package ru.sber;

import java.io.*;
import java.util.logging.LogManager;

public class Application {
    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(
                Application.class.getResourceAsStream("/logging.properties"));
        new ActiveWindowLogger().start(1000);
    }
}
