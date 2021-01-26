package DataBaseBloat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {
    private static Connection conn;

    public DatabaseConn () throws SQLException, ClassNotFoundException {
        conn = connect();
    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        String password = "";
        String user = "silverest";
        String dbLocation = "jdbc:mysql://0.0.0.0:3306/MEDIATHEQUE";
        Connection conn = DriverManager.getConnection(dbLocation, user, password);
        if (conn != null)
            System.out.println(true);
        return conn;
    }

    public static Connection getConn() {
        return conn;
    }
}
