package web.mvc.hrm.repository;

import web.mvc.hrm.model.Employee;
import web.mvc.hrm.util.DatabaseUtility;
import org.apache.commons.validator.GenericValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository {

    public List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtility.connect();
            ps = connection.prepareStatement(SqlQuery.GET_EMPLOYEE_LIST);
            rs = ps.executeQuery();
            while(rs.next()) {
                Employee employee = mapRow(rs);
                employeeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting employee list", e);
        } finally {
            DatabaseUtility.close(rs, ps, connection);
        }

        return employeeList;
    }

    public Optional<Employee> getEmployeeById(long id) {
        Optional<Employee> optionalEmployee = Optional.empty();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtility.connect();
            ps = connection.prepareStatement(SqlQuery.GET_EMPLOYEE_BY_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                Employee employee = mapRow(rs);
                optionalEmployee = Optional.of(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting employee by id " + id, e);
        } finally {
            DatabaseUtility.close(rs, ps, connection);
        }

        return optionalEmployee;
    }

    public Employee update(Employee employee) {

        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connection = DatabaseUtility.connect();
            ps = connection.prepareStatement(SqlQuery.UPDATE_EMPLOYEE);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setLong(4, employee.getId());
            int count = ps.executeUpdate();
            if(count == 1) {
                connection.commit();
                System.out.println("Update employee success");
            } else {
                connection.rollback();
                String error = "Failed to update employee " + employee;
                System.out.println(error);
                throw new RuntimeException(error);
            }
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException("Error updating employee " + employee, e);
        }  finally {
            DatabaseUtility.close(null, ps, connection);
        }

        return employee;
    }

    public long getEmployeeCount() {
        long count = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtility.connect();
            ps = connection.prepareStatement(SqlQuery.GET_EMPLOYEE_COUNT);
            rs = ps.executeQuery();
            if(rs.next()) {
                count = rs.getLong("emp_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting employee count", e);
        } finally {
            DatabaseUtility.close(rs, ps, connection);
        }

        return count;
    }

    public long getEmployeeFilteredCount(String filter) {
        long count = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtility.connect();
            ps = connection.prepareStatement(SqlQuery.GET_EMPLOYEE_FILTERED_COUNT);
            ps.setString(1, "%" + filter + "%");
            rs = ps.executeQuery();
            if(rs.next()) {
                count = rs.getLong("emp_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting employee count", e);
        } finally {
            DatabaseUtility.close(rs, ps, connection);
        }

        return count;
    }

    public List<Employee> getEmployeeListAjax(int start, int length, String sortColumn, String sortDirection, String filter) {
        String sql = null;

        System.out.println("sql = " + sql);
        List<Employee> employeeList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtility.connect();

            if(GenericValidator.isBlankOrNull(filter)) {
                sql = SqlQuery.GET_EMPLOYEE_LIST_AJAX
                        .replace("%SORT_COLUMN%", sortColumn)
                        .replace("%SORT_DIRECTION%", sortDirection);
                ps = connection.prepareStatement(sql);
                ps.setInt(1, length);
                ps.setInt(2, start);
            } else {
                sql = SqlQuery.GET_EMPLOYEE_LIST_FILTERED_AJAX
                        .replace("%SORT_COLUMN%", sortColumn)
                        .replace("%SORT_DIRECTION%", sortDirection);
                ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + filter + "%");
                ps.setInt(2, length);
                ps.setInt(3, start);
            }


            rs = ps.executeQuery();
            while(rs.next()) {
                Employee employee = mapRow(rs);
                employeeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting employee list ajax", e);
        } finally {
            DatabaseUtility.close(rs, ps, connection);
        }

        return employeeList;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        // RowMapper - > java object
        employee.setId(rs.getLong("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setSalary(rs.getBigDecimal("salary"));
        return employee;
    }
}


