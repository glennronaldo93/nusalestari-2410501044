package com.example.nusalestari.util;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private static final String PREF_NAME = "nusalestari_preferences";
    private static final String KEY_DARK_MODE = "dark_mode";

    private ThemeManager() {
    }

    public static boolean isDarkMode(Context context) {
        return context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        ).getBoolean(KEY_DARK_MODE, false);
    }

    public static void setDarkMode(Context context, boolean isDarkMode) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_DARK_MODE, isDarkMode)
                .apply();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            UiModeManager uiModeManager = context.getSystemService(UiModeManager.class);

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