package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by nibbc_000 on 5/10/2017.
 */
public class CustomerDBSetup {

    public static void main(String[] args) throws Exception {


        final String driver = dbConnData.driver;;
        final String url = dbConnData.url;
        final String user = dbConnData.user;
        final String pass = dbConnData.pass;
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE customers ("
                    + "id INTEGER PRIMARY KEY  NOT NULL AUTO_INCREMENT, "
                    + "firstName VARCHAR(255), "
                    + "lastName VARCHAR(255), "
                    + "phoneArea VARCHAR(3), "
                    + "phonePrefix VARCHAR(3), "
                    + "phoneLine VARCHAR(4), "
                    + "address VARCHAR(255) ) "
            );
        }
    }
}
