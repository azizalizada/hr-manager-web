package web.mvc.hrm.web.command;

import web.mvc.hrm.model.Employee;
import web.mvc.hrm.repository.EmployeeRepository;
import web.mvc.hrm.service.ValidationService;
import web.mvc.hrm.web.form.EmployeeForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SaveEmployeeCommand implements WebCommand {

    @Override
    public String getAction() {
        return "save";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeForm form = null;
        try {
            form = parseForm(request);
            ValidationService validationService = new ValidationService();
            Map<String, List<String>> errorMap = validationService.validate(form);

            System.out.println("errorMap = " + errorMap);

            if (errorMap.isEmpty()) {
                // no validation errors, update employee
                request.getSession().removeAttribute("errorMessage");
                Employee employee = parse(form);
                employeeRepository.update(employee);
                response.sendRedirect(request.getContextPath() + "/employee");
            } else {
                // forward to prev page, show validation errors
                request.setAttribute("errorMap", errorMap);
                request.setAttribute("form", form);
                request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-update.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String error = "Isci melumatlarini yenileyen zaman xeta bas verdi, zehmet olmasa, daha sonra tekrar yoxlayin.";
            request.getSession().setAttribute("errorMessage", error);
            response.sendRedirect(request.getContextPath() + "/employee?action=update&id=" + form.getId());
        }
    }

    private EmployeeForm parseForm(HttpServletRequest request) {
        EmployeeForm form = new EmployeeForm();

        if (request.getParameter("id") != null) {
            form.setId(request.getParameter("id").trim());
        }

        if (request.getParameter("first_name") != null) {
            form.setFirstName(request.getParameter("first_name").trim());
        }

        if (request.getParameter("last_name") != null) {
            form.setLastName(request.getParameter("last_name").trim());
        }

        if (request.getParameter("salary") != null) {
            form.setSalary(request.getParameter("salary").trim());
        }

        return form;
    }

    private static Employee parse(EmployeeForm form) {
        Employee employee = new Employee();
        employee.setId(Long.parseLong(form.getId()));
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());
        employee.setSalary(new BigDecimal(form.getSalary()));
        return employee;
    }
}
