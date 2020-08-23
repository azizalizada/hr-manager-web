package web.mvc.hrm.repository.impl;

import web.mvc.hrm.model.Department;
import web.mvc.hrm.repository.DepartmentRepository;
import web.mvc.hrm.repository.SqlQuery;
import web.mvc.hrm.repository.rowmapper.DepartmentRowMapper;
import web.mvc.hrm.util.DatabaseUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryPostgresImpl implements DepartmentRepository {

    @Override
    public List<Department> getDepartmentList() {
        List<Department> departmentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtility.connect();
            ps = connection.prepareStatement(SqlQuery.GET_DEPARTMENT_LIST);
            rs = ps.executeQuery();
            DepartmentRowMapper rowMapper = new DepartmentRowMapper();
            while(rs.next()) {
                Department employee = rowMapper.mapRow(rs);
                departmentList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting employee list", e);
        } finally {
            DatabaseUtility.close(rs, ps, connection);
        }

        return departmentList;
    }


}
