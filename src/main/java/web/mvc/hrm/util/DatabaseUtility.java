package web.mvc.hrm.util;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtility {

    private static DataSource dataSource;

    public static Connection connect() throws Exception {

        if(dataSource == null) {
            InitialContext cxt = new InitialContext();
            if (cxt == null) {
                throw new Exception("Uh oh -- no context!");
            }

            dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/aziz");

            if (dataSource == null) {
                throw new Exception("Data source not found!");
            }
        }

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        return connection;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection connection) {
        try {
            if(rs != null) {
                rs.close();
            }

            if(ps != null) {
                ps.close();
            }

            if(connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
