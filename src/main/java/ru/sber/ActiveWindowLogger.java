package ru.sber;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.util.logging.Logger;

import static com.sun.jna.platform.win32.User32.INSTANCE;
import static java.util.logging.Logger.getLogger;

public class ActiveWindowLogger {
    private final int MAX_TITLE_LENGTH = 1024;
    private final char[] buffer = new char[MAX_TITLE_LENGTH];
    private final Logger log = getLogger(this.getClass().getName());

    public void start(long pollingDelay) {
        new Thread(() -> run(pollingDelay)).start();
    }

    private void run(long pollingDelay) {
        while (true) {
            log.info(getActiveWindowName());
            sleep(pollingDelay);
        }
    }

    private String getActiveWindowName() {
        HWND hwnd = INSTANCE.GetForegroundWindow();
        INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
