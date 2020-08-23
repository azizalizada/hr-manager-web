package web.mvc.hrm.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewEmployeeListAjaxCommand implements WebCommand {
    @Override
    public String getAction() {
        return "viewEmployeeListAjax";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-list-ajax.jsp").forward(request, response);
    }
}
