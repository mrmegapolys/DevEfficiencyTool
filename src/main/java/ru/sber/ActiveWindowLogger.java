package ru.sber;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.IntByReference;

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
            log.info(getActiveWindowInfo());
            sleep(pollingDelay);
        }
    }

    private String getActiveWindowInfo() {
        HWND hwnd = INSTANCE.GetForegroundWindow();
        return String.format("TITLE: %s; PID: %s", getActiveWindowTitle(hwnd), getActiveWindowPID(hwnd));
    }

    private String getActiveWindowTitle(HWND hwnd) {
        INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }

    private Integer getActiveWindowPID(HWND hwnd) {
        IntByReference pidRef = new IntByReference();
        INSTANCE.GetWindowThreadProcessId(hwnd, pidRef);
        return pidRef.getValue();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
