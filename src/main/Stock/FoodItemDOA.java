package main.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FoodItemDOA {
    private Connection connect() throws SQLException {
        String dbURL = "jdbc:sqlite:foodstore.sql";
        return DriverManager.getConnection(dbURL);
    }
    public List<FoodItem> foodItemList() {
        System.out.println("Listing the stock level of the food items...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;

        String query = "SELECT product_id, description, quantity, " +
                "IFNULL(expiry_date,'N/A') expiry_date " +
                "FROM foodproduct , stock " +
                "WHERE foodproduct.id = stock.product_id;";
        List<FoodItem> fooditems = new ArrayList<>();
        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("product_id");
                String product = result.getString("description");
                int quantity = result.getInt("quantity");
                    String expiry_date = result.getString("expiry_date");
                System.out.println("product"+id);
                fooditems.add(new FoodItem(id, product, quantity, expiry_date));
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
        fooditems.forEach(foodItem -> System.out.println(foodItem)); //lambda function to iterate the products in the list.
        return fooditems;
    }
    public List<FoodItem> expiredItems() {
        System.out.println("Listing the stock level of the food items...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;

        String query = "SELECT product_id, description, quantity, " +
                "expiry_date " +
                "FROM foodproduct , stock " +
                "WHERE foodproduct.id = stock.product_id " +
                "AND expiry_date IS NOT NULL " +
                "AND expiry_date < DATE('now');";
        List<FoodItem> fooditems = new ArrayList<>();
        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("product_id");
                String product = result.getString("description");
                int quantity = result.getInt("quantity");
                String expiry_date = result.getString("expiry_date");

                fooditems.add(new FoodItem(id, product, quantity, expiry_date));
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
        fooditems.forEach(foodItem -> System.out.println(foodItem)); //lambda function to iterate the products in the list.
        return fooditems;
    }
    public List<FoodItem> outOfStock() {
        System.out.println("Listing the stock level of the food items...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;

        String query = "SELECT product_id, description, quantity, " +
                "IFNULL(expiry_date,'N/A') expiry_date " +
                "FROM foodproduct , stock " +
                "WHERE foodproduct.id = stock.product_id AND " +
                "quantity=0;";
        List<FoodItem> fooditems = new ArrayList<>();
        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("product_id");
                String product = result.getString("description");
                int quantity = result.getInt("quantity");
                String expiry_date = result.getString("expiry_date");

                fooditems.add(new FoodItem(id, product, quantity, expiry_date));
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
        fooditems.forEach(foodItem -> System.out.println(foodItem)); //lambda function to iterate the products in the list.
        return fooditems;
    }
    public List<FoodItem> reStockItems() {
        System.out.println("Listing the stock level of the food items...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;

        String query = "SELECT product_id, description, quantity, " +
                "expiry_date " +
                "FROM foodproduct , stock " +
                "WHERE foodproduct.id = stock.product_id AND " +
                "(quantity=0 OR " +
                "expiry_date < DATE('now'));";
        List<FoodItem> fooditems = new ArrayList<>();
        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("product_id");
                String product = result.getString("description");
                int quantity = result.getInt("quantity");
                String expiry_date = result.getString("expiry_date");

                fooditems.add(new FoodItem(id, product, quantity, expiry_date));
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
        fooditems.forEach(foodItem -> System.out.println(foodItem)); //lambda function to iterate the products in the list.
        return fooditems;
    }


}


