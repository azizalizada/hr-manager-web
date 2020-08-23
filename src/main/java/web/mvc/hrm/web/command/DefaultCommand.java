package web.mvc.hrm.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultCommand implements WebCommand {
    @Override
    public String getAction() {
        return "default";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/cs?action=viewEmployeeListAjax");
    }
}
