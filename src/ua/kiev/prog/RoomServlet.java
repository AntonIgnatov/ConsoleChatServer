package ua.kiev.prog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Rooms", urlPatterns = "/rooms")
public class RoomServlet extends HttpServlet {
    private UserList userList = UserList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String room = req.getParameter("name");
        String exit = req.getParameter("exit");
        int index = userList.findByName(login);
        if (exit == null){
            userList.getList().get(index).getRooms().add(room);
        }
        if (exit != null && exit.equals("true")) {

            if (userList.getList().get(index).getRooms().contains(room)) {
                userList.getList().get(index).getRooms().remove(room);
            }
        }
    }
}
