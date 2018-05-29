package ua.kiev.prog;

import java.util.ArrayList;

import java.util.List;

public class JsonMessages {
    private List<Message> list;

    public JsonMessages(List<Message> sourceList, int fromIndex, String login) {
        this.list = new ArrayList<>();
        UserList userList = UserList.getInstance();
        int index = userList.findByName(login);
        for (int i = fromIndex; i < sourceList.size(); i++)
            if (messageToAll(sourceList.get(i)) ||
                    messagePrivate(sourceList.get(i), login) ||
                    messageToRoom(sourceList.get(i), userList, index) ||
                    ownMessage(sourceList.get(i), login)) {
                list.add(sourceList.get(i));
            }
    }

    public static boolean messageToAll(Message message) {
        return (message.getRoom() == null && message.getTo() == null);
    }

    public static boolean messagePrivate(Message message, String login) {
        return (message.getTo() != null && (message.getFrom().equals(login) || message.getTo().equals(login)));
    }

    public static boolean messageToRoom(Message message, UserList userList, int index) {
        return (message.getRoom() != null && userList.getList().get(index).getRooms().contains(message.getRoom()));
    }

    public static boolean ownMessage(Message message, String login) {
        return message.getFrom().equals(login);
    }
}
