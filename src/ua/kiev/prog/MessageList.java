package ua.kiev.prog;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
    private static final MessageList msgList = new MessageList();
    private static final int LIMIT = 100;

    private final Gson gson;
    private final List<Message> list = new LinkedList<Message>();
    private UserList userList = UserList.getInstance();

    public static synchronized MessageList getInstance() {
        return msgList;
    }

    private MessageList() {
        gson = new GsonBuilder().create();
    }

    public synchronized void add(Message m) {
        String login = m.getFrom();
        int index = userList.findByName(login);
        if (userList.getList().get(index).isOnline()) {
            list.add(m);
        }
    }

    public synchronized String toJSON(int n, String login) {
        if (n == list.size()) return null;
        return gson.toJson(new JsonMessages(list, n, login));
    }
}
