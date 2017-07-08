package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;


public class customerConnector {
    static final String driver = dbConnData.driver;;
    static final String url = dbConnData.url;
    static final String user = dbConnData.user;
    static final String pass = dbConnData.pass;

    public static int addCustomer(Customer c) throws ClassNotFoundException, SQLException {
        int id = 0 ;
        try {
                Class.forName(driver);
                Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();
                id = stmt.executeUpdate( "INSERT INTO customers (firstName,lastName,phoneArea,phonePrefix,phoneLine,address) VALUES ( '"+c.getFirst_name()+"','"+c.getLast_name()+"','"+c.getPhone_area()+"','"+c.getPhone_prefix()+"','"+c.getPhone_phone()+"','"+c.getAddress()+"')", Statement.RETURN_GENERATED_KEYS);

                System.out.println("customer saved to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static ObservableList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException {
        ObservableList<Customer> data = FXCollections.observableArrayList();

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                Customer c = new Customer(rs.getInt("customerId"),rs.getString("customerName"),"","","","","");
                data.add(c);
            }
            System.out.println("customer saved to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void getAllCustomersList(ListLoader listLoader) throws ClassNotFoundException, SQLException {
        ArrayList<Customer> data = new ArrayList();

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                Customer c = new Customer(rs.getInt("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("phoneArea"),rs.getString("phonePrefix"),rs.getString("phoneLine"),rs.getString("address"));
                data.add(c);
            }
            System.out.println("customer saved to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        listLoader.update_customer_list(data);
    }

    public static boolean updateCustomer(int customer_id, String columnName, String value) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();
            stmt.executeUpdate( "UPDATE customers SET "+columnName+"="+value+" WHERE id="+customer_id);

            System.out.println("customer updated");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void delete_customer(int id) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();
            stmt.executeUpdate( "DELETE FROM customers WHERE id="+id);

            System.out.println("customer removed");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
