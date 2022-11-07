package com.example.f22comp1011w9s1;

import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBUtility {
    //to access a MySQL server, we need the user name, password,
    //ip address and port #
    private static String user = "student";
    private static String pw = "student";

    //jdbc:mysql = Java DataBase Connector to MySQL Server
    //127.0.0.1 = IP address of the server (127.0.0.1 is the local host)
    //3306 = port # that MySQL server is accessible on
    //F22 = database name
    private static String connURL = "jdbc:mysql://127.0.0.1:3306/F22Midterm";

    //We also updated the pom.xml with the maven information and added requires java.sql
    //to the module-info file

    /**
     * This method will return an ArrayList<CountryCode> with all countries represented
     */
    public static ArrayList<Customer> getCustomers()
    {
        ArrayList<Customer> customers = new ArrayList<>();

        //create a SQL statement
        String sql = "SELECT * FROM customers";

        //connecting to a DB can trigger a SQL exception, so we do the coding
        //inside of a try...catch block
        try(
                //anything inside these () are "auto-closed", the system will
                //automatically call the .close() method
                //1. Connect to the DB
                Connection conn = DriverManager.getConnection(connURL, user, pw);

                //2.  Create a statement object
                Statement statement = conn.createStatement();

                //3.  use the statement object to run the sql
                ResultSet resultSet = statement.executeQuery(sql);
        )
        {
            //loop over the resultSet and create CountryCode objects
            while (resultSet.next())
            {
                int customerId = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String province = resultSet.getString("province");
                String bloodType = resultSet.getString("bloodType");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();

                Customer customer = new Customer(customerId, firstName, lastName,
                                                    province, bloodType,birthday);
                customers.add(customer);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return customers;
    }

    public static XYChart.Series<String, Integer> getCustomersByBloodType() {
        XYChart.Series<String, Integer> customers = new XYChart.Series<>();

        String sql = "SELECT bloodType, COUNT(id) AS count " +
                "FROM customers " +
                "GROUP BY bloodType;";

        try (
                Connection conn = DriverManager.getConnection(connURL, user, pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            //loop over the resultSet and create PieChart.Data objects
            while (resultSet.next()) {
                String name = resultSet.getString("bloodType");
                int count = resultSet.getInt("count");
                customers.getData().add(new XYChart.Data<>(name,count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static XYChart.Series<String, Integer> getCustomersByProvince() {
        XYChart.Series<String, Integer> customers = new XYChart.Series<>();

        String sql = "SELECT province, COUNT(id) AS count " +
                "FROM customers " +
                "GROUP BY province;";

        try (
                Connection conn = DriverManager.getConnection(connURL, user, pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            //loop over the resultSet and create PieChart.Data objects
            while (resultSet.next()) {
                String name = resultSet.getString("province");
                int count = resultSet.getInt("count");
                customers.getData().add(new XYChart.Data<>(name,count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
}
