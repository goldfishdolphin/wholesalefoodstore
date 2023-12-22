import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class DeleteHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException{

        FoodProductDAO foodProducts = new FoodProductDAO();
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        String response = "failed";
        int id = Integer.parseInt(map.get("id"));
        foodProducts.deleteProduct(id);
        try {
            Optional<FoodProduct> optionalFoodProduct = Optional.ofNullable(foodProducts.selectProduct(id));
            if (optionalFoodProduct.isEmpty())
                System.out.println("Not found");
//                response = "Product not found";
            else{
                foodProducts.deleteProduct(id);
                response = "Product deleted";
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
