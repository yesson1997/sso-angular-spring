package com.sonle.azuresso.controller.reqres;

import java.util.List;

public class GetUsersResponse {
    private List<PublicUser> users;

    public GetUsersResponse(List<PublicUser> users) {
        this.users = users;
    }

    public List<PublicUser> getUsers() {
        return users;
    }

    public void setUsers(List<PublicUser> users) {
        this.users = users;
    }
}
