package com.epam.popcornapp.model.base;

import io.realm.Realm;

public class BaseInteractor {

    private Realm realm;

    protected void openRealm() {
        if (realm == null || realm.isClosed()) {
            this.realm = Realm.getDefaultInstance();
        }
    }

    protected void closeRealm() {
        this.realm.close();
    }

    protected Realm getRealm() {
        return this.realm;
    }
}
