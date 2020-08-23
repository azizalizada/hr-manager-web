package web.mvc.hrm.web;

import web.mvc.hrm.model.DataTableResponse;
import web.mvc.hrm.model.Employee;
import web.mvc.hrm.repository.EmployeeRepository;
import web.mvc.hrm.service.ValidationService;
import web.mvc.hrm.web.form.EmployeeForm;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String action = "viewEmployeeList";

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        }

        EmployeeRepository employeeRepository = new EmployeeRepository();

        if (action != null && !action.isEmpty()) {
            if (action.equals("viewEmployeeList")) {
                List<Employee> employeeList = employeeRepository.getEmployeeList();
                System.out.println("employee count = " + employeeList.size());
//                employeeList.forEach(System.out::println);
                request.setAttribute("employeeList", employeeList);
                request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-list.jsp").forward(request, response);
            } else if (action.equals("viewEmployeeListAjax")) {
                request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-list-ajax.jsp").forward(request, response);
            } else if (action.equals("view")) {
                long id = Long.parseLong(request.getParameter("id"));
                Optional<Employee> optionalEmployee = employeeRepository.getEmployeeById(id);
                if (optionalEmployee.isPresent()) {
                    Employee employee = optionalEmployee.get();
                    request.setAttribute("employee", employee);
                }
                request.getRequestDispatcher("/WEB-INF/jsp/employee/employee.jsp").forward(request, response);
            } else if (action.equals("update")) {
                long id = Long.parseLong(request.getParameter("id"));
                Optional<Employee> optionalEmployee = employeeRepository.getEmployeeById(id);
                if (optionalEmployee.isPresent()) {
                    Employee employee = optionalEmployee.get();
                    request.setAttribute("employee", employee);
                }
                request.getRequestDispatcher("/WEB-INF/jsp/employee/employee-update.jsp").forward(request, response);
            } else if (action.equals("save")) {
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
            } else if(action.equals("getEmployeeList")) {
                /*
                draw - int, geri qaytaririq
                start - int nechenci setirden basla
                length - neche setir data qaytar
                order[0][column] - int hansi sutuna gore sirala 0-dan baslayir
                order[0][dir] - string asc/desc siralama\
                search[value] - string filter
                * */
                String draw = request.getParameter("draw");
                int start = Integer.parseInt(request.getParameter("start"));
                int length = Integer.parseInt(request.getParameter("length"));
                int sortColumn = Integer.parseInt(request.getParameter("order[0][column]"));
                String sortDirection = request.getParameter("order[0][dir]");
                String filter = request.getParameter("search[value]");

                DataTableResponse ajaxResponse = new DataTableResponse();
                ajaxResponse.setDraw(Integer.parseInt(draw));
                ajaxResponse.setRecordsTotal(employeeRepository.getEmployeeCount());

                if(filter != null && !filter.isEmpty()) {
                    ajaxResponse.setRecordsFiltered(employeeRepository.getEmployeeFilteredCount(filter));
                } else {
                    ajaxResponse.setRecordsFiltered(ajaxResponse.getRecordsTotal());
                }

                Map<Integer, String> columnMapping = new HashMap<>();
                columnMapping.put(1, "employee_id");
                columnMapping.put(2, "first_name");
                columnMapping.put(3, "last_name");
                columnMapping.put(4, "salary");

                  /*
                0 - employee_id
                1 - first_name
                2 - last_name
                3 - email
                4 - phone_numeric
                5 - hire_date
                6 - job_id
                7 - salary
                8 - commission_pct
                9 - manager_id
                10 - department_id
                        */

                List<Employee> employeeList = employeeRepository.getEmployeeListAjax(
                        start,
                        length,
                        columnMapping.getOrDefault(sortColumn, "employee_id"),
                        sortDirection,
                        filter);

                Object[][] data = new Object[employeeList.size()][6];
                for (int i = 0; i < employeeList.size(); i++) {
                    Employee employee = employeeList.get(i);
                    data[i][0] = i + 1;
                    data[i][1] = employee.getId();
                    data[i][2] = employee.getFirstName();
                    data[i][3] = employee.getLastName();
                    data[i][4] = employee.getSalary();
                    /*
                    data[i][5] = String.format("<a href='employee?action=view&id=%d'>View</a> &nbsp;" +
                            "<a href='employee?action=update&id=%d'>Update</a> &nbsp;" +
                            "<a href='employee?action=delete&id=%d'>Delete</a>",
                            employee.getId(), employee.getId(), employee.getId());
                     */
                    data[i][5] = String.format("<a href='cs?action=view&id=%d'>View</a> &nbsp;" +
                                    "<a href='cs?action=update&id=%d'>Update</a> &nbsp;" +
                                    "<a href='cs?action=delete&id=%d'>Delete</a>",
                            employee.getId(), employee.getId(), employee.getId());
                }
                ajaxResponse.setData(data);

                System.out.println("ajax response = " + ajaxResponse);
                Gson gson = new Gson();
                String json = gson.toJson(ajaxResponse);
                System.out.println("json = " + json);
                response.setContentType("application/json");
                response.getWriter().print(json);
            }
        }else if(action.equals("delete")){


        }else {
            action = "viewEmployeeList";
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
