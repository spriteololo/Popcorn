package com.epam.popcornapp.pojo.account;

public class AccountSimpleData {

    private String userName;
    private String name;
    private String gravatarHash;

    public AccountSimpleData(final String userName, final String name, final String gravatarHash) {
        this.userName = userName;
        this.name = name;
        this.gravatarHash = gravatarHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getGravatarHash() {
        return gravatarHash;
    }

    public void setGravatarHash(final String gravatarHash) {
        this.gravatarHash = gravatarHash;
    }
}
