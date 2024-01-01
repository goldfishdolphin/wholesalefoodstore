package main.Stock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;

import static main.Util.getCurrentSessionId;

public class StockHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

        String sessionId = getCurrentSessionId(he);
        String loggedInUser = null;
        if (sessionId != null) {
            loggedInUser = SessionManager.getLoggedInUser(sessionId);
            System.out.println("Logged in as: " + loggedInUser);
        } else {
            System.out.println("No Session ID found.");
        }
        FoodItemDOA foodItemDOA= new FoodItemDOA();
        List<FoodItem> fooditems = foodItemDOA.foodItemList();

        System.out.println("hello stock page");


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
                        "<h1> Food Products !</h1>" +
                        "<div class=\"dropdown\">" +
                        "      <button class=\"btn btn-success dropdown-toggle\" type=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
                        "        Filter Stock" +
                        "      </button>" +
                        "      <ul class=\"dropdown-menu\" style=\"\">" +
                        "        <li><a class=\"dropdown-item\" href=\"stock=expired\">Expired Items</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"stock=outofstock\">Out of Stock</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"stock=restock\">Stock to Re-order</a></li>" +
                        "        <li><a class=\"dropdown-item\" href=\"/stock\">All Stock</a></li>" +
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
                        "    <th>ID</th>" +
                        "    <th>Product Details</th>" +
                        "    <th>Quantity</th>" +
                        "    <th>Expiry Date</th>" +
                        "  </tr>" +
                        "</thead>" +
                        "<tbody>");
        for (FoodItem f : fooditems) {
            out.write(f.toHTMLString());
        }
        out.write(
                "</tbody>" +
                        "</table>");
        if (Objects.equals(loggedInUser, "admin")) {
            out.write("<a href=\"/add\" class=\"btn btn-dark\"> Add New Product </a>" +
                    "<a href=\"/customers\" class=\"btn btn-dark\"> Customer</a>");
        }

        if (loggedInUser != null) {
            out.write("<a href=\"/logout\" class=\"btn btn-dark\"> Log Out</a>");
        } else {
            out.write("<a href=\"/login\" class=\"btn btn-dark\"> Log In</a>");
            out.write("<a href=\"/stock\" class=\"btn btn-dark\"> Check Stock</a>");

        }

        out.write("</body>" +
                "</html>"
        );

        out.close();

    }
}
