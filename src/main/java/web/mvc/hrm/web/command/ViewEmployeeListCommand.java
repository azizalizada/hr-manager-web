package web.mvc.hrm.web.command;

import web.mvc.hrm.model.Employee;
import web.mvc.hrm.repository.EmployeeRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewEmployeeListCommand implements WebCommand{
    @Override
    public String getAction() {
        return "viewEmployeeList";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        List<Employee> employeeList = employeeRepository.getEmployeeList();
        System.out.println("employee count = " + employeeList.size());
//                employeeList.forEach(System.out::println);
        request.setAttribute("employeeList", employeeList);
        request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-list.jsp").forward(request, response);
    }
}
