package main.Customer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {
    /**
     * This function create a connection java class and the database
     *
     * @return the connectivity with sqlite
     * @throws SQLException if there is an issue with the database.
     */
    private Connection connect() throws SQLException {

        String dbURL = "jdbc:sqlite:foodstore.sql";
        return DriverManager.getConnection(dbURL);
    }

    public List<Customer> listCustomer() {
        System.out.println("Listing all customers...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;
        String query = "SELECT * FROM customer;";
        List<Customer> customers = new ArrayList<>();

        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("customer_id");
                String businessName = result.getString("business_name");
                String phoneNumber = result.getString("telephone_number");
                String addressLine1 = result.getString("address_line1");
                String addressLine2 = result.getString("address_line2");
                String addressLine3 = result.getString("address_line3");
                String country = result.getString("country");
                String postCode = result.getString("post_code");
                var address = new Address(addressLine1, addressLine2, addressLine3, postCode, country);

                customers.add(new Customer(id, businessName, address, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                try {
                    result.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        customers.forEach(customer -> System.out.println(customer));
        return customers;
    }

    public Customer selectCustomer(int customer_id) throws SQLException {

        Customer customer = null;
        Connection dbConnection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String query = "SELECT * FROM customer WHERE customer_id =?;";

        try {
            dbConnection = connect();
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, customer_id);
            result = statement.executeQuery();

            while (result.next()) {
                customer_id = result.getInt("customer_id");
                String businessName = result.getString("business_name");
                String phoneNumber = result.getString("telephone_number");
                String addressLine1 = result.getString("address_line1");
                String addressLine2 = result.getString("address_line2");
                String addressLine3 = result.getString("address_line3");
                String country = result.getString("country");
                String postCode = result.getString("post_code");
                var address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
                customer = new Customer(customer_id, businessName, address, phoneNumber);
            }
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (customer == null)
            System.out.println("The customer does not exist with the id " + customer_id);
        return customer;
    }

    public void deleteCustomer(int customer_id) {
        System.out.println("Deleting the customer");
        Connection dbConnection = null;
        PreparedStatement statement = null;
        int result;
        String query = "DELETE FROM customer WHERE customer_id =?;";
        try {
            dbConnection = connect();
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, customer_id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (dbConnection != null) {
            try {
                dbConnection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The customer with the id " + customer_id + " has been deleted!");
    }


    public void upsert(Customer customer) {
        PreparedStatement statement = null;
        Connection connection = null;

        boolean ok = false;
        try {
            connection = connect();

            String query = "INSERT OR REPLACE INTO customer VALUES(?, ?, ?, ?, ?,?, ?, ?);";
            statement = connection.prepareStatement(query);
            customer.assignParameters(statement);

            statement.executeUpdate();
            statement.close();
            connection.close();
            ok = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }
}


