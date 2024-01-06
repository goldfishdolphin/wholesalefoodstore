package main.ShoppingBasket;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Stock.FoodItemDOA;
import main.User.SessionManager;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.Util.getCurrentSessionId;

public class BasketHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        int id = Integer.parseInt(map.get("id"));

        FoodItemDOA stockItem = new FoodItemDOA();
        String item = null;
        try {

            item = String.valueOf(stockItem.selectItem(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> mapBasketItem = Util.itemKeyValuePairs(item);
        String product = mapBasketItem.get("Product");
        int unit = 1;
        double unitPrice = Double.parseDouble(mapBasketItem.get("Price"));
        double totalPrice = unit * unitPrice;


        ShoppingBasketDAO basketDAO = new ShoppingBasketDAO();
        ShoppingBasket item1 = new ShoppingBasket(id, product, unit, unitPrice, totalPrice);
        basketDAO.upsert(item1);

        String sessionId = getCurrentSessionId(he);
        String loggedInUser = null;
        if (sessionId != null) {
            loggedInUser = SessionManager.getLoggedInUser(sessionId);
            System.out.println("Logged in as: " + loggedInUser);
        } else {
            System.out.println("No Session ID found.");
        }


        out.write(
                "<html>" +

                        "<head>" +
                        "    <meta charset=\"utf-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                        "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                        "    <link href=\"https://getbootstrap.com/docs/5.3/assets/css/docs.css\" rel=\"stylesheet\">" +
                        "    <title>The Food Store</title>" +
                        "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"></script>" +
                        "  </head>" +
                        "<body>" +
                        "<h1> Item Added to the Basket!</h1>" +
                        "<a href=\"/\" class=\"btn btn-success\"> Continue Shopping</a>"+
                        "<a href=\"/viewbasket\" class=\"btn btn-dark\">Go to Basket</a>"+
                        "</body>" +
                        "</html>"
        );

        out.close();

    }

}

