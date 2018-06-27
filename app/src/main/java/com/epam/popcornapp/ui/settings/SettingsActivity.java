package com.epam.popcornapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingsActivity extends BaseMvpActivity implements SettingsView {

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    @BindView(R.id.transparent_toolbar)
    Toolbar toolbar;

    public static Intent start(@NonNull final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onViewsBinded() {
        setToolbar(toolbar, getString(R.string.settings), true);

        settingsPresenter.refreshData();
    }


    @Override
    public void setInfo(final List<String[]> data) {
        final SettingsFragment settingsFragment = (SettingsFragment)
                getFragmentManager().findFragmentById(R.id.settings_fragment);

        settingsFragment.setData(data);
    }

    @Override
    public void onError(@ErrorType final int errorType) {

    }

    public static class SettingsFragment extends PreferenceFragment {

        final List<ListPreference> listPreferences = new ArrayList<>();

        SharedPreferences.OnSharedPreferenceChangeListener changeListener;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
                    findPreference(key).setSummary(sharedPreferences.getString(key, ""));
                }
            };

            initializeSettings();
        }

        @Override
        public void onResume() {
            super.onResume();

            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(changeListener);
        }

        @Override
        public void onPause() {
            super.onPause();

            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(changeListener);
        }

        private void initializeSettings() {
            final PreferenceManager preferenceManager = getPreferenceManager();
            final Context context = getActivity();

            listPreferences.add((ListPreference) preferenceManager
                    .findPreference(context.getString(R.string.backdrop_size)));
            listPreferences.add((ListPreference) preferenceManager
                    .findPreference(context.getString(R.string.logo_size)));
            listPreferences.add((ListPreference) preferenceManager
                    .findPreference(context.getString(R.string.poster_size)));
            listPreferences.add((ListPreference) preferenceManager
                    .findPreference(context.getString(R.string.profile_size)));
            listPreferences.add((ListPreference) preferenceManager
                    .findPreference(context.getString(R.string.still_size)));
        }


        public void setData(final List<String[]> data) {
            int i = 0;

            for (final ListPreference listPreference : listPreferences) {
                setListPreference(listPreference, data.get(i), data.get(i));
                i++;
            }
        }

        private void setListPreference(@NonNull final ListPreference listPreference,
                                       final String[] entries,
                                       final String[] entryValues) {
            listPreference.setEntries(entries);
            listPreference.setEntryValues(entryValues);
            listPreference.setSummary(listPreference.getValue());
        }
    }
}
