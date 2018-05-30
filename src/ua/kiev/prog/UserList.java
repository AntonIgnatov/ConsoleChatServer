package ua.kiev.prog;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static final UserList userList = new UserList();
    private final List<User> list = new ArrayList<>();


    private UserList() {
        super();
    }

    public static synchronized UserList getInstance() {
        return userList;
    }

    public List<User> getList() {
        return list;
    }

    public synchronized boolean existing(String name, String password) {
        for (User u : list) {
            if (u.getLogin().equals(name) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean checkLogin(String lolin) {
        for (User u : this.list) {
            if (u.getLogin().equals(lolin)) {
                return false;
            }
        }
        return true;
    }

    public synchronized int findByName(String name) {
        for (int i = 0; i < list.size(); i++) {
            if (this.list.get(i).getLogin().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void deleteByName(String name) {
        int index = this.findByName(name);
        this.list.get(index).setOnline(false);
    }


    public synchronized void add(String login, String password) {
        list.add(new User(login, password));
    }

    public List<String> usersStatus() {
        List<String> result = new ArrayList<>();

        for (User u : userList.getList()) {
            String online = u.isOnline() ? "online" : "ofline";
            result.add(u.getLogin() + " is " + online);
        }
        return result;
    }
    public List<String> usersStatus(String login) {
        List<String> result = new ArrayList<>();

        for (User u : userList.getList()) {
            if (u.getLogin().equals(login)){
                String online = u.isOnline() ? "online" : "ofline";
                result.add(u.getLogin() + " is " + online);
            }

        }
        return result;
    }
//    public synchronized String toJSON(int n) {
//        if (n == list.size()) return null;
//        return gson.toJson(new JsonMessages(list, n));
//    }
}
