package web.mvc.hrm.repository.rowmapper;

import web.mvc.hrm.model.Department;
import web.mvc.hrm.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentRowMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getLong("department_id"));
        department.setName(rs.getString("department_name"));
        if(rs.getLong("employee_id") > 0) {
            EmployeeRowMapper employeeRowMapper = new EmployeeRowMapper();
            Employee manager = employeeRowMapper.mapRow(rs);
            department.setManager(manager);
        }
        return department;
    }
}
