package main.Product;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

/**
 * This class creates an object to delete a product by its id.
 */
public class DeleteHandler implements HttpHandler {
    /**
     * This method removes the item by id.
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    public void handle(HttpExchange he) throws IOException {

        FoodProductDAO foodProducts = new FoodProductDAO();
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        int id = Integer.parseInt(map.get("id"));
        foodProducts.deleteProduct(id);
        try {
            Optional<FoodProduct> optionalFoodProduct = Optional.ofNullable(foodProducts.selectProduct(id));
            System.out.println(optionalFoodProduct);
            if (optionalFoodProduct.isEmpty())
                System.out.println("Not found");
            else {
                foodProducts.deleteProduct(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        he.getResponseHeaders().set("Location", "/");
        he.sendResponseHeaders(302, 0);
        he.getResponseBody().close();
    }

}
