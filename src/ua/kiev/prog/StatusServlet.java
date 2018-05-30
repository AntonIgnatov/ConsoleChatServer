package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserStatus", urlPatterns = "/userstatus")
public class StatusServlet extends HttpServlet {
    private UserList userList = UserList.getInstance();
    Gson gson = new GsonBuilder().create();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");

        String list;
        if (login != null && userList.findByName(login) != -1) {
            list = gson.toJson(userList.usersStatus(login));
            OutputAnswer.outputWrite(resp, list);
        }
        if (login != null && userList.findByName(login) == -1) {
            OutputAnswer.outputWrite(resp, "wrong user");
        }

        if (login == null) {
            list = gson.toJson(userList.usersStatus());
            if (list != null) {
                OutputAnswer.outputWrite(resp, list);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
