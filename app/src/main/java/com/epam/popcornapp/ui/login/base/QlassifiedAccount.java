package com.epam.popcornapp.ui.login.base;

import android.content.Context;
import android.text.TextUtils;

import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.q42.qlassified.Qlassified;
import com.q42.qlassified.Storage.QlassifiedSharedPreferencesService;

public class QlassifiedAccount {

    public QlassifiedAccount(final Context context) {
        Qlassified.Service.start(context);
        Qlassified.Service.setStorageService(
                new QlassifiedSharedPreferencesService(
                        context, context.getString(R.string.qlassified_storage_name)));
    }

    public void saveSessionId(final String sessionId) {
        Qlassified.Service.put(Constants.QLASSIFIED_SESSION_ID_KEY, sessionId);
    }

    public void saveAccountId(final int accountId) {
        Qlassified.Service.put(Constants.QLASSIFIED_ACCOUNT_ID_KEY, Integer.toString(accountId));
    }

    public void saveUserName(final String login) {
        Qlassified.Service.put(Constants.QLASSIFIED_USERNAME_KEY, login);
    }

    public void saveName(final String userName) {
        Qlassified.Service.put(Constants.QLASSIFIED_NAME_KEY, userName);
    }

    public void saveGravatarHash(final String gravatarHash) {
        Qlassified.Service.put(Constants.QLASSIFIED_GRAVATAR_HASH_KEY, gravatarHash);
    }

    public String getSessionId() {
        final String sessionId = Qlassified.Service.getString(Constants.QLASSIFIED_SESSION_ID_KEY);

        return TextUtils.isEmpty(sessionId) ? null : sessionId;
    }

    public int getAccountId() {
        return Integer.parseInt(Qlassified.Service.getString(Constants.QLASSIFIED_ACCOUNT_ID_KEY));
    }

    public String getUserName() {
        return Qlassified.Service.getString(Constants.QLASSIFIED_USERNAME_KEY);
    }

    public String getName() {
        return Qlassified.Service.getString(Constants.QLASSIFIED_NAME_KEY);
    }

    public String getGravatarHash() {
        return Qlassified.Service.getString(Constants.QLASSIFIED_GRAVATAR_HASH_KEY);
    }

    public void clearAll() {
        Qlassified.Service.put(Constants.QLASSIFIED_SESSION_ID_KEY, Constants.EMPTY_STRING);
        Qlassified.Service.put(Constants.QLASSIFIED_ACCOUNT_ID_KEY, Constants.EMPTY_ID);
        Qlassified.Service.put(Constants.QLASSIFIED_NAME_KEY, Constants.EMPTY_STRING);
        Qlassified.Service.put(Constants.QLASSIFIED_GRAVATAR_HASH_KEY, Constants.EMPTY_STRING);
    }
}
