package com.greenkeycompany.exam.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by Alexander on 08.05.2017.
 */

public class PremiumUtil {

    public static final String PREMIUM_USER_SKU = "com.greenkeycompany.russian.premium";
    private static final String PREMIUM_USER_KEY = "premium_user";

    private static boolean premiumUser;
    private static SharedPreferences sharedPreferences;

    static void init(@NonNull Context context) {
        PremiumUtil.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        PremiumUtil.premiumUser = sharedPreferences.getBoolean(PREMIUM_USER_KEY, true);
    }

    public static boolean isPremiumUser() {
        return premiumUser;
    }

    public static void setPremiumUser(boolean premiumUser) {
        PremiumUtil.premiumUser = premiumUser;
        PremiumUtil.sharedPreferences.edit().putBoolean(PREMIUM_USER_KEY, premiumUser).apply();
    }
}
