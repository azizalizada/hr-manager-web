package web.mvc.hrm.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WebCommand {
    String getAction();
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
