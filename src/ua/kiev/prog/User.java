package ua.kiev.prog;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    private boolean online = true;
    private List<String> rooms = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
