package ua.kiev.prog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddMessage", urlPatterns = "/add")
public class AddServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        Message msg = Message.fromJSON(bufStr);
        boolean f = false;
        try {
            f = checkMessage(msg);
        } catch (ArrayIndexOutOfBoundsException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            e.printStackTrace();
        }
        if (msg != null && f) {
            msgList.add(msg);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }

    private static boolean checkMessage(Message message) throws ArrayIndexOutOfBoundsException {
        UserList userList = UserList.getInstance();
        String room = message.getRoom();
        String to = message.getTo();
        int index = userList.findByName(to);
        User tempUserTo = null;
        if (index != -1) {
            tempUserTo = userList.getList().get(index);
        }
        index = userList.findByName(message.getFrom());
        User tempUserFrom = userList.getList().get(index);
        if (room != null && tempUserFrom.getRooms().contains(room)) {
            return true;
        }
        if (to != null && tempUserTo != null) {
            return true;
        }
        if (to == null && room == null) {
            return true;
        }
        return false;
    }
}
