package web.mvc.hrm.web.command;

import web.mvc.hrm.model.Employee;
import web.mvc.hrm.repository.EmployeeRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ViewEmployeeCommand implements WebCommand{
    @Override
    public String getAction() {
        return "view";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        long id = Long.parseLong(request.getParameter("id"));
        Optional<Employee> optionalEmployee = employeeRepository.getEmployeeById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            request.setAttribute("employee", employee);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/employee/employee.jsp").forward(request, response);
    }
}
