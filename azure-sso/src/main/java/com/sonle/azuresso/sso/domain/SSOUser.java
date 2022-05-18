package com.sonle.azuresso.sso.domain;

public class SSOUser {
    private String displayName;
    private String surname;
    private String givenName;
    private String userPrincipalName;

    public SSOUser() {
    }

    public SSOUser(String displayName, String surname, String givenName, String userPrincipalName) {
        this.displayName = displayName;
        this.surname = surname;
        this.givenName = givenName;
        this.userPrincipalName = userPrincipalName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    @Override
    public String toString() {
        return "SSOUser{" +
                "displayName='" + displayName + '\'' +
                ", surname='" + surname + '\'' +
                ", givenName='" + givenName + '\'' +
                ", userPrincipalName='" + userPrincipalName + '\'' +
                '}';
    }
}
