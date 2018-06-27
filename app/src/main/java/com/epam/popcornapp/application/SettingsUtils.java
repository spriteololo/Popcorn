package com.epam.popcornapp.application;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.epam.popcornapp.R;

public class SettingsUtils {

    public static String getBackdropSize(@NonNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.backdrop_size), context.getString(R.string.default_value));
    }

    public static String getLogoSize(@NonNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.logo_size), context.getString(R.string.default_value));
    }

    public static String getPosterSize(@NonNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.poster_size), context.getString(R.string.default_value));
    }

    public static String getProfileSize(@NonNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.profile_size), context.getString(R.string.default_value));
    }

    public static String getStillSize(@NonNull final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.still_size), context.getString(R.string.default_value));
    }
}
