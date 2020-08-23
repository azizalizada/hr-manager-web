package web.mvc.hrm.repository;

public class SqlQuery {

    public static final String GET_EMPLOYEE_LIST = "select employee_id, first_name, last_name, salary " +
            "from employees " +
            "order by first_name, last_name";

    public static final String GET_EMPLOYEE_BY_ID = "select employee_id, first_name, last_name, salary " +
            "from employees " +
            "where employee_id = ? " +
            "order by first_name, last_name";

    public static final String UPDATE_EMPLOYEE = "update employees " +
            "set first_name = ?, last_name = ?, salary = ? " +
            "where employee_id = ?";

    public static final String GET_EMPLOYEE_COUNT = "select count(employee_id) as emp_count " +
            "from employees ";

    public static final String GET_EMPLOYEE_LIST_AJAX = "select employee_id, first_name, last_name, salary \n" +
            "from employees\n " +
            "order by %SORT_COLUMN% %SORT_DIRECTION%\n " +
            "limit ? offset ?";

    public static final String GET_EMPLOYEE_LIST_FILTERED_AJAX = "select employee_id, first_name, last_name, salary \n" +
            "from employees\n " +
            "where concat(employee_id, first_name, last_name, salary) like ? " +
            "order by %SORT_COLUMN% %SORT_DIRECTION%\n " +
            "limit ? offset ?";

    public static final String GET_EMPLOYEE_FILTERED_COUNT = "select count(employee_id) emp_count\n" +
            "from employees\n" +
            "where concat(employee_id, first_name, last_name, salary) like ?";

    public static final String GET_DEPARTMENT_LIST = "select d.department_id, d.department_name, \n" +
            "\te.employee_id, e.first_name, e.last_name, e.salary\n " +
            "from departments d left join employees e on d.manager_id = e.employee_id\n " +
            "order by d.department_name";
}
