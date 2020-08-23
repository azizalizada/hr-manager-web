package web.mvc.hrm.web.command;

import web.mvc.hrm.model.Department;
import web.mvc.hrm.repository.DepartmentRepository;
import web.mvc.hrm.repository.impl.DepartmentRepositoryPostgresImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewDepartmentListCommand implements WebCommand{
    @Override
    public String getAction() {
        return "viewDepartmentList";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepartmentRepository departmentRepository = new DepartmentRepositoryPostgresImpl();
        List<Department> departmentList = departmentRepository.getDepartmentList();
        request.setAttribute("departmentList", departmentList);
        request.getRequestDispatcher("/WEB-INF/jsp/department/department-list.jsp").forward(request, response);
    }
}
