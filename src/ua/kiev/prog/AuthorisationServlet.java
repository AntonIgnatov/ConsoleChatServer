package ua.kiev.prog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Authorisation", urlPatterns = "/authoris")
public class AuthorisationServlet extends HttpServlet {
    private UserList userList = UserList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String exit = req.getParameter("exit");
        int index = userList.findByName(login);

        if (exit != null && exit.equals("true")) {
            userList.deleteByName(login);
            OutputAnswer.outputWrite(resp, login + " leave chat(");
        } else {
            if (userList.existing(login, password)) {

                userList.getList().get(index).setOnline(true);
                OutputAnswer.outputWrite(resp, "You connected as " + login);
            } else if (index == -1) {
                userList.add(login, password);
                OutputAnswer.outputWrite(resp, "You connected as " + login);
            } else {
                OutputAnswer.outputWrite(resp, "Login " + login + " is allready in use");
            }
        }
    }
}
