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

public class BasketViewHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
//        String request = he.getRequestURI().getQuery();
//        HashMap<String, String> map = Util.requestStringToMap(request);
//        int id = Integer.parseInt(map.get("id"));
        long totalBasketValue = 0;
//        FoodItemDOA stockItem = new FoodItemDOA();
//        String item = null;
//        try {
//
//            item = String.valueOf(stockItem.selectItem(id));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        Map<String, String> mapBasketItem = Util.itemKeyValuePairs(item);
//        String product = mapBasketItem.get("Product");
//        int unit = 1;
//        long unitPrice = Long.parseLong(mapBasketItem.get("Price"));
//        long totalPrice = unit * unitPrice;
//
//
        ShoppingBasketDAO basketDAO = new ShoppingBasketDAO();
//        ShoppingBasket item1 = new ShoppingBasket(id, product, unit, unitPrice, totalPrice);
//        basketDAO.upsert(item1);
//        System.out.println("item" + item1);

        List<ShoppingBasket> basketList = basketDAO.listBasketItems();


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
                        "<h1> Shopping Basket !</h1>" +
                        "<div class=\"dropdown\">" +
                        "      <button class=\"btn btn-success dropdown-toggle\" type=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
                        "        Choose Category" +
                        "      </button>" +
                        "      <ul class=\"dropdown-menu\" style=\"\">" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Fruit\">Fruit</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Vegetable\">Vegetable</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Rice\">Rice</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Cold Drink\">Cold Drink</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Frozen Food\">Frozen Food</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"filter?Category=Snack\">Snack</a></li>" +
                        "<li><a class=\"dropdown-item\" href=\"/\">All</a></li>" +
                        "      </ul>" +
                        "    </div>" +


                        "<nav class=\"navbar bg-body-tertiary\">" +
                        "      <div class=\"container-fluid\">" +
                        "<form class=\"d-flex\" method=\"GET\" action=\"/search\">" +
                        "          <input" +
                        "            class=\"form-control me-2\"" +
                        "            type=\"search\"" +
                        "            name=\"query\"" +
                        "            placeholder=\"Search Products\"" +
                        "            aria-label=\"Search\"" +
                        "          />" +
                        "          <button class=\"btn btn-outline-success\" type=\"submit\">Search</button>" +
                        "        </form>" +
                        "      </div>" +
                        "    </nav>" +
                        "<table class=\"table\">" +
                        "<thead>" +
                        "  <tr>" +
                        "    <th>Product ID</th>" +
                        "    <th>Product</th>" +
                        "    <th>Quantity</th>" +
                        "    <th>Unit Price</th>" +
                        "    <th>Total Price</th>" +
                        "  </tr>" +
                        "</thead>" +
                        "<tbody>");
        for (ShoppingBasket b : basketList) {
            out.write(b.toHTMLString());
            totalBasketValue = totalBasketValue + b.getTotalPrice();
        }

        out.write(
                "</tbody>" +
                        "</table>" +

                        "<div class=\"grid display-6 text-center text-lg\">" +
                        "  <div class=\"g-col-3 display-6 g-start-2\">Total Basket Amount</div>" +
                        "  <div class=\"g-col-4 display-6  g-start-6\">" + totalBasketValue + "</div>" +
                        "</div>" +

                        "<a href=\"/\" class=\"btn btn-success\"> Continue Shopping</a>"+
                        "<a href=\"/checkout\" class=\"btn btn-dark\">Check Out</a>"+
                        "<a href=\"/clear\" class=\"btn btn-danger\">Clear Basket</a>"+
                        "</body>" +
                        "</html>"
        );

        out.close();

    }

}

