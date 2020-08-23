package web.mvc.hrm.repository.rowmapper;

import web.mvc.hrm.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee>{
    @Override
    public Employee mapRow(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        // RowMapper - > java object
        employee.setId(rs.getLong("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setSalary(rs.getBigDecimal("salary"));
        return employee;
    }
}
