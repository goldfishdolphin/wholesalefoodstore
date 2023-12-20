import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class FoodProductDAO {
     /**
      * This function create a connection java class and the database
      * @return the connectivity with sqlite
      * @throws SQLException
      */
     private Connection connect() throws SQLException {

         String dbURL = "jdbc:sqlite:foodstore.sql";
         return DriverManager.getConnection(dbURL);
     }
     public List<FoodProduct> listProduct(){
         System.out.println("Listing all products...");
         Connection dbConnection =null;
         Statement statement = null;
         ResultSet result = null;
         String query = "SELECT * FROM foodproduct;";
//         System.out.println(query);
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
         } catch (SQLException e){
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
         /**
          * lambda function to iterate each food products in the array list.
          */
         foodProducts.forEach(System.out::println);
     return foodProducts;
 }
 }
