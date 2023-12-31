package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FoodProductDAO {
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

    public List<FoodProduct> listProduct() {
        System.out.println("Listing all products...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;
        String query = "SELECT * FROM foodproduct;";
        List<FoodProduct> foodProducts = new ArrayList<>();

        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("ID");
                String SKU = result.getString("SKU");
                String description = result.getString("description");
                String category = result.getString("category");
                long price = result.getLong("price");

                foodProducts.add(new FoodProduct(id, SKU, description, category, price));
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
        foodProducts.forEach(foodProduct -> System.out.println(foodProduct)); //lambda function to iterate the products in the list.
        return foodProducts;
    }

    public FoodProduct selectProduct(int product_id) throws SQLException {

        FoodProduct product = null;
        Connection dbConnection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String query = "SELECT * FROM foodproduct WHERE ID =?;";

        try {
            dbConnection = connect();
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, product_id);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String SKU = result.getString("SKU");
                String description = result.getString("Description");
                String category = result.getString("Category");
                long price = result.getLong("Price");
                product = new FoodProduct(id, SKU, description, category, price);
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
        if (product == null)
            System.out.println("The product does not exist with the id " + product_id);
        return product;
    }

    public void deleteProduct(int product_id) {
        System.out.println("Deleting the product");
        Connection dbConnection = null;
        PreparedStatement statement = null;
        int result;
        String query = "DELETE FROM foodproduct WHERE ID =?;";
        try {
            dbConnection = connect();
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, product_id);
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
        System.out.println("The product with id " + product_id + " has been deleted!");
    }


    public void upsert(FoodProduct product) {
        PreparedStatement statement = null;
        Connection connection = null;

        boolean ok = false;
        try {
            connection = connect();

            String query = "INSERT OR REPLACE INTO foodproduct VALUES(?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(query);
            product.assignParameters(statement);

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

