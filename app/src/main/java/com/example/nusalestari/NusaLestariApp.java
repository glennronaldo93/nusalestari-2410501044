package com.example.nusalestari;

import android.app.Application;
import android.app.UiModeManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

public class NusaLestariApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        boolean isDarkMode = getSharedPreferences(
                "nusalestari_preferences",
                MODE_PRIVATE
        ).getBoolean("dark_mode", false);

        applyTheme(isDarkMode);
    }

    private void applyTheme(boolean isDarkMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            UiModeManager uiModeManager = getSystemService(UiModeManager.class);

            if (uiModeManager != null) {
                uiModeManager.setApplicationNightMode(
                        isDarkMode
                                ? UiModeManager.MODE_NIGHT_YES
                                : UiModeManager.MODE_NIGHT_NO
                );
            }
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    isDarkMode
                            ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
        }
    }
}