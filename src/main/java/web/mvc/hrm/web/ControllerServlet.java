package web.mvc.hrm.web;

import web.mvc.hrm.web.command.DefaultCommand;
import web.mvc.hrm.web.command.WebCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ControllerServlet", urlPatterns = "/cs")
public class ControllerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, WebCommand> commandMap = (Map<String, WebCommand>) getServletContext().getAttribute("commandMap");
        System.out.println("command map = " + commandMap);
        WebCommand command;
        DefaultCommand defaultCommand = new DefaultCommand();
        String action = request.getParameter("action");
        if(action != null) {
            action = request.getParameter("action").trim();
            command = commandMap.getOrDefault(action, defaultCommand);
        } else {
            command = defaultCommand;
        }

        System.out.println(action + " = " + command.getClass().getName());
        command.execute(request, response);

        // todo implement exception handling and forward to ErrorCommand
    }
}
