package main.ShoppingBasket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketDAO {
    private Connection connect() throws SQLException {
        String dbURL = "jdbc:sqlite:foodstore.sql";
        return DriverManager.getConnection(dbURL);
    }

    public List<ShoppingBasket> listBasketItems() {
        System.out.println("Listing all Basket items...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;
        String query = "SELECT product_id, product, " +
                "count(product_id) AS units, " +
                "price FROM basket " +
                "GROUP BY product_id;";
        List<ShoppingBasket> basketItems = new ArrayList<>();

        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            statement.clearBatch();
            result = statement.executeQuery(query);
            if (!result.next()) {
                System.out.println("No row found in the basket table");
            } else {
                do {

                    int id = result.getInt("product_id");
                    String product = result.getString("product");
                    int units = result.getInt("units");
                    double unitPrice = result.getDouble("price");
                    double totalPrice = unitPrice * units;

                    basketItems.add(new ShoppingBasket(id, product, units, unitPrice, totalPrice));
                } while (result.next());
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
        basketItems.forEach(item -> System.out.println(item)); //lambda function to iterate the items in the list.
        return basketItems;
    }

    public void upsert(ShoppingBasket item) {
        PreparedStatement statement = null;
        Connection connection = null;

        boolean ok = false;
        try {
            connection = connect();
            String query = "INSERT OR REPLACE INTO basket VALUES(?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(query);
            item.assignParameters(statement);

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

    public void emptyBasket() {
        System.out.println("Emptying the shopping basket");
        Connection dbConnection = null;
        PreparedStatement statement = null;

        try {
            dbConnection = connect();
            String query = "DELETE FROM basket;";
            statement = dbConnection.prepareStatement(query);
            int result = statement.executeUpdate();
            System.out.println("Number of rows deleted from basket table: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
