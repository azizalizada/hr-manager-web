package web.mvc.hrm.web.command;

import web.mvc.hrm.model.DataTableResponse;
import web.mvc.hrm.model.Employee;
import web.mvc.hrm.repository.EmployeeRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetEmployeeListCommand implements WebCommand {
    @Override
    public String getAction() {
        return "getEmployeeList";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
                draw - int, geri qaytaririq
                start - int nechenci setirden basla
                length - neche setir data qaytar
                order[0][column] - int hansi sutuna gore sirala 0-dan baslayir
                order[0][dir] - string asc/desc siralama\
                search[value] - string filter
                * */

        EmployeeRepository employeeRepository = new EmployeeRepository();
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
            data[i][5] = String.format("<a href='employee?action=view&id=%d'>View</a> &nbsp;" +
                            "<a href='employee?action=update&id=%d'>Update</a> &nbsp;" +
                            "<a href='employee?action=delete&id=%d'>Delete</a>",
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
}
