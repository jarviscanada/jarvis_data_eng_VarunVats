package ca.jrvs.apps.jdbc;
import java.sql.ResultSet;

import ca.jrvs.apps.jdbc.util.Customer;
import ca.jrvs.apps.jdbc.util.CustomerDAO;

import java.sql.Connection;

import java.sql.Statement;
import java.sql.SQLException;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost","hplussport", "postgres","password");
        try {
            Connection connection = dcm.getConnection();
           // Statement statement = connection.createStatement();
            // ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
          //  while (resultSet.next()) {
            //    System.out.println(resultSet.getInt(1));
           // }


            // Read from Database

            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("George");
            customer.setLastName("Washington");
            customer.setEmail("george.washington@wh.gov");
            customer.setPhone("(555) 555-6543");
            customer.setAddress("1234 Main St");
            customer.setCity("Mount Vernon");
            customer.("VA");






        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
