package main.Stock;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.User.SessionManager;
import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static main.Util.getCurrentSessionId;

/**
 * This class filters the stock by its expiry level and display them
 */

public class ExpiryHandler implements HttpHandler {
    /**
     * This method iterate the expiry status of the stock.
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        String request = he.getRequestURI().getQuery();
        HashMap<String, String> map = Util.requestStringToMap(request);
        String status = map.get("stock");
        System.out.println(status);
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


        FoodItemDOA foodItems = new FoodItemDOA();
        List<FoodItem> fooditems = null;

        if (status.equals("expired")) {
            fooditems = foodItems.expiredItems();
        } else if (status.equals("outofstock")) {
            fooditems = foodItems.outOfStock();
        } else if (status.equals("restock")) {
            fooditems = foodItems.reStockItems();
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
                        "<body>" );
                        out.write("<div class=\"card \">" +
                                "      <img class=\"card-img\" src=\"https://cdn.pixabay.com/photo/2017/08/05/12/33/flat-lay-2583213_1280.jpg\"  alt=\"Card image\">" +
                                "      <div class=\"card-img-overlay text-end\">" +
                                "<h1 class=\"text-center\"> The Food Store !</h1>" +
                                "    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                                "      <div class=\"container-fluid\">" +
                                "        <a class=\"navbar-brand\" href=\"/\">Food Store</a>" +
                                "        <button class=\"navbar-toggler\" type=\"button\" " +
                                "data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                                "          <span class=\"navbar-toggler-icon\"></span>" +
                                "        </button>" +
                                "        <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown\">" +
                                "          <ul class=\"navbar-nav\">" +
                                "            <li class=\"nav-item\">" +
                                "              <a class=\"nav-link\" href=\"/\">Home</a>" +
                                "            </li>" +
                                "            <li class=\"nav-item\">" +
                                " <a class=\"nav-link\" href=\"/customers\">Customers</a>" +
                                "            </li>" +
                                "            <li class=\"nav-item\">" +
                                "<a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Stock</a>" +
                                "            </li>" +
                                "            </li>" +
                                "          </ul>" +
                                "        </div>" +
                                "<nav class=\"navbar bg-body-tertiary navbar-dark bg-dark \">" +
                                "      <div class=\"container-fluid \">" +
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
                                "      </div>" +
                                "    </nav>" +

                                "<div class=\"hstack gap-3\">" +

                                "  <div class=\"p-2 ms-auto\">" +
                                " <div class=\"dropdown\">" +
                                "      <button class=\"btn btn-danger dropdown-toggle\" type=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
                                "        Filter Stock" +
                                "      </button>" +
                                "      <ul class=\"dropdown-menu\" style=\"\">" +
                                "        <li><a class=\"dropdown-item\" href=\"status?stock=expired\">Expired Items</a></li>" +
                                "        <li><a class=\"dropdown-item\" href=\"status?stock=outofstock\">Out of Stock</a></li>" +
                                "        <li><a class=\"dropdown-item\" href=\"status?stock=restock\">Stock to Re-order</a></li>" +
                                "        <li><a class=\"dropdown-item\" href=\"/stock\">All Stock</a></li>" +
                                "      </ul>" +
                                "    </div>" );

        if (loggedInUser != null) {
            out.write("<a href=\"/logout\" class=\"btn btn-dark p-3 mt-2\"> Log Out</a>");
        } else {
            out.write("<a href=\"/login\" class=\"btn btn-dark p-3 m-2\"> Log In</a>");


        }
        out.write("  <div class=\"p-2 ms-auto\">" +
                "</div>" +
                " <div class=\"vr\"></div>" +
                "<div class=\"p-2\">" +
                "<table class=\"table  table-light table-striped\">" +
                "<thead>" +
                "  <tr>" +
                "    <th>ID</th>" +
                "    <th>SKU</th>" +
                "    <th>Description</th>" +
                "    <th>Category</th>" +
                "    <th>Price</th>" +
                "  </tr>" +
                "</thead>" +
                "<tbody>");
        for (FoodItem f : fooditems) {
            out.write(f.toHTMLString());
        }
        out.write("</tbody>" +
                "</table>");
        out.write("</body>" +
                "</html>"
        );

        out.close();

    }
}
